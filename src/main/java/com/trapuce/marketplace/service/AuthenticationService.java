package com.trapuce.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trapuce.marketplace.dtos.LoginUserDto;
import com.trapuce.marketplace.dtos.RegisterUserDto;
import com.trapuce.marketplace.mappers.UserMapper;
import com.trapuce.marketplace.models.EmailConfirmationToken;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.models.UserPrincipal;
import com.trapuce.marketplace.repository.EmailConfirmationTokenRepository;
import com.trapuce.marketplace.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailConfirmationTokenRepository emailConfirmationTokenRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService ;

    public User signup(RegisterUserDto registerUserDto) {

        User newUser = this.userMapper.RegisterUserDtoToUser(registerUserDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRegistration_date(new Date());
        newUser.setIs_professional(true);
        newUser.setAccountVerified(false);
        User user = userRepository.save(newUser);
        this.sendRegistrationConfirmationEmail(user);
        return user;
    }

    @Transactional
    public boolean verifyUser(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
    
        EmailConfirmationToken emailConfirmationToken = emailConfirmationTokenRepository
            .findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid confirmation token"));
    
        User user = emailConfirmationToken.getUser();
        if (user == null) {
            throw new IllegalArgumentException("No user found for this token");
        }
    
        if (user.isAccountVerified()) {
            throw new IllegalStateException("Account is already verified");
        }
    
        if (emailConfirmationToken.getTimeStamp().plusHours(24).isBefore(LocalDateTime.now())) {
            emailConfirmationTokenRepository.delete(emailConfirmationToken);
            throw new IllegalStateException("Token has expired");
        }
    
        user.setAccountVerified(true);
        userRepository.save(user);
        
        emailConfirmationTokenRepository.delete(emailConfirmationToken);
    
        return true;
    }

    public void sendRegistrationConfirmationEmail(User user) {
        String tokenValue = this.jwtService.generateToken(new UserPrincipal(user));
        EmailConfirmationToken emailConfirmationToken = new EmailConfirmationToken();
        emailConfirmationToken.setToken(tokenValue);
        emailConfirmationToken.setTimeStamp(LocalDateTime.now());
        emailConfirmationToken.setUser(user);
        emailConfirmationTokenRepository.save(emailConfirmationToken);
        this. emailService.sendConfirmationEmail(emailConfirmationToken);
    }

    public UserPrincipal authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()));

        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserPrincipal(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
