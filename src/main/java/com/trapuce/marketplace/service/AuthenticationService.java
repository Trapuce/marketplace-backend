package com.trapuce.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.AuthResponseDTO;
import com.trapuce.marketplace.dtos.CreateUserDTO;
import com.trapuce.marketplace.dtos.IdTokenRequestDto;
import com.trapuce.marketplace.dtos.LoginUserDto;
import com.trapuce.marketplace.exceptions.AccountAlreadyVerifiedException;
import com.trapuce.marketplace.exceptions.InvalidTokenException;
import com.trapuce.marketplace.exceptions.TokenExpiredException;
import com.trapuce.marketplace.exceptions.UserNotFoundException;
import com.trapuce.marketplace.mappers.UserMapper;
import com.trapuce.marketplace.models.Token;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.models.UserPrincipal;
import com.trapuce.marketplace.repository.TokenRepository;
import com.trapuce.marketplace.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.security.GeneralSecurityException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final GoogleIdTokenVerifier verifier;
    private final String clientId;

    @Autowired
    public AuthenticationService(
            @Value("${security.oauth2.client.registration.google.client-id}") String clientId,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            UserMapper userMapper,
            TokenRepository tokenRepository,
            JwtService jwtService,
            EmailService emailService) throws GeneralSecurityException, IOException {

        this.clientId = clientId;

        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        this.verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public User register(CreateUserDTO createUserDTO) {
        if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = userMapper.CreateUserDTOToUser(createUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(new Date());
        user.setAccountVerified(false);
        user = userRepository.save(user);

        sendRegistrationConfirmationEmail(user);

        return user;
    }

    public AuthResponseDTO authenticateWithGoogle(IdTokenRequestDto requestDto)
            throws IllegalArgumentException, IOException {

        User user = verifyGoogleIdToken(requestDto.getIdToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired Google ID token"));

        user = createOrUpdateUser(user);
        String token = jwtService.generateToken(new UserPrincipal(user));

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();

        authResponseDTO.setUser(userMapper.UserToUserDTO(user));

        authResponseDTO.setToken(token);

        return authResponseDTO;

    }

    private Optional<User> verifyGoogleIdToken(String idToken) throws IOException {

        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");
            String email = payload.getEmail();
            String pictureUrl = (String) payload.get("picture");
            User user = new User();

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            return Optional.of(user);
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException("Invalid or expired Google ID token");
        }

    }

    @Transactional
    protected User createOrUpdateUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(existingUser -> updateExistingGoogleUser(existingUser, user))
                .orElseGet(() -> createNewGoogleUser(user));
    }

    private User updateExistingGoogleUser(User existingUser, User user) {

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return userRepository.save(existingUser);
    }

    private User createNewGoogleUser(User user) {

        user.setAccountVerified(true);
        user.setRegistrationDate(new Date());
        user.setPassword(null);
        return userRepository.save(user);
    }

    @Transactional
    public boolean verifyUser(String token) {

        if (token == null || token.trim().isEmpty()) {
            throw new InvalidTokenException("Token cannot be null or empty");
        }

        Token emailConfirmationToken = this.tokenRepository
                .findByTokenValue(token)
                .orElseThrow(() -> new InvalidTokenException("Invalid confirmation token"));

        User user = emailConfirmationToken.getUser();
        if (user == null) {
            throw new UserNotFoundException("No user found for this token");
        }

        if (user.isAccountVerified()) {
            throw new AccountAlreadyVerifiedException("Account is already verified");
        }

        if (emailConfirmationToken.getTimeStamp().plusHours(24).isBefore(LocalDateTime.now())) {

            this.tokenRepository.delete(emailConfirmationToken);
            throw new TokenExpiredException("Token has expired");
        }

        user.setAccountVerified(true);
        userRepository.save(user);

        this.tokenRepository.delete(emailConfirmationToken);

        return true;
    }

    public boolean verifyResetToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidTokenException("Token cannot be null or empty");
        }

        Token resetToken = this.tokenRepository
                .findByTokenValue(token.trim())
                .orElseThrow(() -> new InvalidTokenException("Invalid reset token"));

        User user = resetToken.getUser();
        if (user == null) {
            tokenRepository.delete(resetToken);
            throw new UserNotFoundException("No user found for this token");
        }

        if (resetToken.getTimeStamp().plusHours(24).isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            throw new TokenExpiredException("Reset token has expired");
        }

        return true;
    }

    @Transactional
    private void sendTokenEmail(User user, String emailType) {
        tokenRepository.deleteByUser(user);

        String newToken = jwtService.generateToken(new UserPrincipal(user));
        Token emailConfirmationToken = new Token();
        emailConfirmationToken.setTokenValue(newToken);
        emailConfirmationToken.setUser(user);
        emailConfirmationToken = tokenRepository.save(emailConfirmationToken);

        if ("RESET".equals(emailType)) {
            emailService.sendPasswordResetEmail(emailConfirmationToken);
        } else {
            emailService.sendConfirmationEmail(emailConfirmationToken);
        }
    }

    public void resetPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        sendPasswordResetEmail(user);
    }

    public void sendPasswordResetEmail(User user) {
        sendTokenEmail(user, "RESET");
    }

    public void sendRegistrationConfirmationEmail(User user) {
        sendTokenEmail(user, "CONFIRMATION");
    }

    public AuthResponseDTO login(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserPrincipal newUserPrincipal = new UserPrincipal(user);
        String token = jwtService.generateToken(newUserPrincipal);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);
        authResponseDTO.setUser(userMapper.UserToUserDTO(user));

        return authResponseDTO;

    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public void updatePassword(String token, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }

        Token resetToken = tokenRepository
                .findByTokenValue(token.trim())
                .orElseThrow(() -> new IllegalArgumentException("Invalid reset token"));

        User user = resetToken.getUser();
        if (user == null) {
            tokenRepository.delete(resetToken);
            throw new IllegalArgumentException("No user found for this token");
        }

        user.setPassword(passwordEncoder.encode(newPassword.trim()));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }

}
