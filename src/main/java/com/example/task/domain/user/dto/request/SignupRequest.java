package com.example.task.domain.user.dto.request;

import com.example.task.domain.user.entity.User;
import com.example.task.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupRequest {
        private String username;
        private String email;
        private String password;

        public static User toEntity(SignupRequest request, String password) {
            return User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(password)
                    .userRole(UserRole.ROLE_USER)
                    .build();
        }
}
