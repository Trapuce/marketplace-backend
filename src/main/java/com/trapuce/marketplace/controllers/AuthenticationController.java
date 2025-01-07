package com.trapuce.marketplace.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapuce.marketplace.dtos.LoginUserDto;
import com.trapuce.marketplace.dtos.RegisterUserDto;
import com.trapuce.marketplace.models.LoginResponse;
import com.trapuce.marketplace.models.User;
import com.trapuce.marketplace.models.UserPrincipal;
import com.trapuce.marketplace.service.AuthenticationService;
import com.trapuce.marketplace.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
/**
 * Controller for managing user authentication and registration.
 */
@Tag(name = "Authentication", description = "API for user authentication and registration")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


        /**
     * Constructs the AuthenticationController.
     *
     * @param jwtService           Service for generating and validating JWT tokens.
     * @param authenticationService Service for authenticating and registering users.
     */
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }


     /**
     * Endpoint to register a new user.
     *
     * @param registerUserDto Data transfer object containing user registration details.
     * @return ResponseEntity containing the created user object and HTTP status.
     */
    @Operation(summary = "Register a new user", description = "Registers a new user and returns the created user details.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User createdUser = this.authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }


    /**
     * Endpoint to authenticate a user.
     *
     * @param loginUserDto Data transfer object containing user login credentials.
     * @return ResponseEntity containing the JWT token or error message.
     */
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token on success.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Authentication successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        try {
            System.out.println("email " + loginUserDto.getEmail());
            UserPrincipal authenticatedUser = authenticationService.authenticate(loginUserDto);

            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during authentication: " + e.getMessage());
        }
    }
}
