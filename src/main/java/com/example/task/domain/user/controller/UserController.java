package com.example.task.domain.user.controller;

import com.example.task.domain.user.dto.request.LoginRequest;
import com.example.task.domain.user.dto.request.SignupRequest;
import com.example.task.domain.user.dto.response.LoginResponse;
import com.example.task.domain.user.dto.response.SignupResponse;
import com.example.task.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse signupResponse = userService.signup(request);
        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
