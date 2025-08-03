package com.example.task.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.example.task.common.code.ErrorCode;
import com.example.task.common.exception.CustomException;
import com.example.task.common.jwt.PasswordEncoder;
import com.example.task.common.jwt.TokenProvider;
import com.example.task.domain.user.dto.request.LoginRequest;
import com.example.task.domain.user.dto.request.SignupRequest;
import com.example.task.domain.user.dto.response.LoginResponse;
import com.example.task.domain.user.dto.response.SignupResponse;
import com.example.task.domain.user.entity.User;
import com.example.task.domain.user.entity.UserRole;
import com.example.task.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("회원가입 성공")
    void signup_Success() {
        // given
        SignupRequest request = SignupRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
        
        String encodedPassword = "encodedPassword123";
        User savedUser = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password(encodedPassword)
                .userRole(UserRole.ROLE_USER)
                .build();
        
        given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
        given(passwordEncoder.encode(request.getPassword())).willReturn(encodedPassword);
        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // when
        SignupResponse response = userService.signup(request);

        // then
        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getRoles()).contains("ROLE_USER");
        
        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 이메일")
    void signup_Fail_DuplicateEmail() {
        // given
        SignupRequest request = SignupRequest.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .build();
        
        given(userRepository.existsByEmail(request.getEmail())).willReturn(true);

        // when, then
        assertThatThrownBy(() -> userService.signup(request))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("responseCode", ErrorCode.USER_ALREADY_EXISTS);
        
        verify(userRepository).existsByEmail(request.getEmail());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_Success() {
        // given
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password123")
                .build();
        
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword123")
                .userRole(UserRole.ROLE_USER)
                .build();
        
        String accessToken = "access.token.jwt";
        String refreshToken = "refresh.token.jwt";
        
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(request.getPassword(), user.getPassword())).willReturn(true);
        given(tokenProvider.createAccessToken(user)).willReturn(accessToken);
        given(tokenProvider.createRefreshToken(user)).willReturn(refreshToken);

        // when
        LoginResponse response = userService.login(request);

        // then
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);
        
        verify(userRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).matches(request.getPassword(), user.getPassword());
        verify(tokenProvider).createAccessToken(user);
        verify(tokenProvider).createRefreshToken(user);
    }

    @Test
    @DisplayName("로그인 실패 - 사용자 없음")
    void login_fail_userNotFound() {
        // given
        LoginRequest request = LoginRequest.builder()
                .email("nonexistent@example.com")
                .password("password123")
                .build();
        
        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("responseCode", ErrorCode.USER_NOT_FOUND);
        
        verify(userRepository).findByEmail(request.getEmail());
    }

}
