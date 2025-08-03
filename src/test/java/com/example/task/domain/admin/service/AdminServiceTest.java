package com.example.task.domain.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.task.common.code.ErrorCode;
import com.example.task.common.exception.CustomException;
import com.example.task.domain.admin.dto.response.UpdateUserRoleResponse;
import com.example.task.domain.user.entity.User;
import com.example.task.domain.user.entity.UserRole;
import com.example.task.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("권한 업데이트 성공 - ADMIN 변경")
    void updateUserRole_success() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("password123")
                .userRole(UserRole.ROLE_USER)
                .build();
        
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when
        UpdateUserRoleResponse response = adminService.updateUserRole(userId);

        // then
        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getRoles()).contains("ROLE_ADMIN");
        assertThat(user.getUserRole()).isEqualTo(UserRole.ROLE_ADMIN);
        
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("권한 업데이트 실패 - 사용자 없음")
    void updateUserRole_fail_userNotFound() {
        // given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> adminService.updateUserRole(userId))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("responseCode", ErrorCode.USER_NOT_FOUND);
        
        verify(userRepository).findById(userId);
    }
}
