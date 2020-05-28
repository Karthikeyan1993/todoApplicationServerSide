package com.app.todo.controller;

import com.app.todo.payload.UserDetail;
import com.app.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("active")
    public ResponseEntity<UserDetail> getUserDetails(Authentication auth){
        return new ResponseEntity<>(new UserDetail(auth.getName()), HttpStatus.OK);
    }

    @GetMapping("isUsernameExists/{username}")
    public ResponseEntity<Boolean> isUserNameExists(@PathVariable String username){
        return new ResponseEntity<>(this.userRepository.existsByUsername(username),HttpStatus.OK);
    }

    @GetMapping("isEmailExists/{email}")
    public ResponseEntity<Boolean> isEmailExists(@PathVariable String email){
        return new ResponseEntity<>(this.userRepository.existsByEmail(email),HttpStatus.OK);
    }
}
