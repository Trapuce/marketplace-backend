package com.trapuce.marketplace.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trapuce.marketplace.dtos.*;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * Controller for managing user authentication and registration.
 */
@Tag(name = "Authentication", description = "API for user authentication and registration")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint to register a new user.
     *
     * @param createUserDTO Data transfer object containing user registration details.
     * @return ResponseEntity containing the created user object and HTTP status.
     */
    @Operation(summary = "Register a new user", description = "Registers a new user and returns the created user details.")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Validation error")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        User createdUser = authenticationService.register(createUserDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Endpoint to authenticate a user.
     *
     * @param loginUserDto Data transfer object containing user login credentials.
     * @return ResponseEntity containing the JWT token or error message.
     */
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token on success.")
    @ApiResponse(responseCode = "200", description = "Authentication successful")
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        AuthResponseDTO authResponse = authenticationService.login(loginUserDto);
        return ResponseEntity.ok(authResponse);
    }

    /**
     * Endpoint for Google OAuth2 login.
     */
    @PostMapping("/google-login")
    public ResponseEntity<AuthResponseDTO> loginWithGoogleOauth2(@RequestBody IdTokenRequestDto requestBody) throws IOException {
        AuthResponseDTO authResponse = authenticationService.authenticateWithGoogle(requestBody);
        return ResponseEntity.ok(authResponse);
    }

    /**
     * Endpoint to confirm email verification.
     */
    @GetMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token) {
        try {
            boolean isVerified = authenticationService.verifyUser(token);
            if (isVerified) {
                return ResponseEntity.ok("Your email has been successfully verified.");
            } else {
                return ResponseEntity.badRequest().body("Invalid confirmation token.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email verification failed.");
        }
    }

    /**
     * Endpoint to display the forgot password page.
     */
    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "security/forgotPassword";
    }

    /**
     * Endpoint to request password reset.
     */
    @PostMapping("/password-reset")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {
        try {
            authenticationService.resetPassword(email);
            return ResponseEntity.ok("Password reset instructions sent to your email.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Password reset failed: " + e.getMessage());
        }
    }

    /**
     * Endpoint to verify password change token.
     */
    @GetMapping("/password-change")
    public ResponseEntity<String> passwordChange(@RequestParam("token") String token) {
        try {
            boolean isVerified = authenticationService.verifyResetToken(token);
            return isVerified
                    ? ResponseEntity.ok("Password change verification successful.")
                    : ResponseEntity.badRequest().body("Invalid token.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Password change verification failed: " + e.getMessage());
        }
    }

    /**
     * Endpoint to change password.
     */
    @PostMapping("/password-change")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestBody PasswordChangeRequest request) {
        try {
            authenticationService.updatePassword(token, request.getPassword());
            return ResponseEntity.ok("Password reset successful.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Password reset failed: " + e.getMessage());
        }
    }

    /**
     * Endpoint to log out the user.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authenticationService.logout();
        return ResponseEntity.ok("User logged out successfully");
    }
}
