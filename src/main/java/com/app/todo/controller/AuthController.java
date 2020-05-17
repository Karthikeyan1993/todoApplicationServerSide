package com.app.todo.controller;

import com.app.todo.entity.User;
import com.app.todo.payload.ApiResponse;
import com.app.todo.payload.JwtAuthenticationResponse;
import com.app.todo.payload.LoginRequest;
import com.app.todo.repository.UserRepository;
import com.app.todo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    public AuthController(@Qualifier("userRepository") UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserNameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody User user) {
        if (this.userRepository.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username already exists"), HttpStatus.BAD_REQUEST);
        }
        if (this.userRepository.existsByEmail(user.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "User Registered Successfully"), HttpStatus.ACCEPTED);
    }
}
