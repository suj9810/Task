package com.example.task.domain.user.service;

import com.example.task.common.code.ErrorCode;
import com.example.task.common.exception.CustomException;
import com.example.task.common.jwt.PasswordEncoder;
import com.example.task.common.jwt.TokenProvider;
import com.example.task.domain.user.dto.request.LoginRequest;
import com.example.task.domain.user.dto.request.SignupRequest;
import com.example.task.domain.user.dto.response.LoginResponse;
import com.example.task.domain.user.dto.response.SignupResponse;
import com.example.task.domain.user.entity.User;
import com.example.task.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public SignupResponse signup(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        String encoded = passwordEncoder.encode(request.getPassword());

        User user = SignupRequest.toEntity(request, encoded);

        return SignupResponse.of(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = tokenProvider.createAccessToken(user);
        String refreshToken = tokenProvider.createRefreshToken(user);

        return LoginResponse.of(accessToken, refreshToken);
    }
}
