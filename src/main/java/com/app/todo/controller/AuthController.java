package com.app.todo.controller;

import com.app.todo.Exception.UserException;
import com.app.todo.entity.Token;
import com.app.todo.entity.User;
import com.app.todo.payload.*;
import com.app.todo.repository.TokenRepository;
import com.app.todo.repository.UserRepository;
import com.app.todo.security.JwtTokenProvider;
import com.app.todo.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final MailService mailService;

    private final TokenRepository tokenRepository;

    public AuthController(@Qualifier("userRepository") UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, MailService mailService, @Qualifier("tokenRepository") TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.mailService = mailService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUserNameOrEmail(),
                        signInRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication.getName());
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        if (this.userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username already exists"), HttpStatus.BAD_REQUEST);
        }
        if (this.userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return new ResponseEntity<>(new ApiResponse(true, "User Registered Successfully"), HttpStatus.ACCEPTED);
    }

    @GetMapping("getResetPasswordLink/{email}")
    public ResponseEntity<?> sendResetPasswordLink(@PathVariable String email) throws MessagingException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = this.tokenProvider.generateToken(user.getUsername());
        this.tokenRepository.save(new Token(user.getUsername(), token));
        this.mailService.sendForgotPasswordMail(user.getEmail(), token);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws UserException {
        User user = this.userRepository.findByUsernameOrEmail(request.getUserNameOrEmail(), request.getUserNameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Token token = this.tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new UserException("Token Not Found For User"));
        if (this.tokenProvider.validateToken(token.getToken())) {
            if (user.getUsername().equals(token.getUsername())) {
                user.setPassword(this.bCryptPasswordEncoder.encode(request.getPassword()));
                this.userRepository.saveAndFlush(user);
                return new ResponseEntity<>(new ApiResponse(true, "Password changed successfully"), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ApiResponse(false, "Invalid User Details Or Token"), HttpStatus.BAD_REQUEST);
    }

}
