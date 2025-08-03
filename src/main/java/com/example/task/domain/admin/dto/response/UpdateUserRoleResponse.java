package com.example.task.domain.admin.dto.response;

import com.example.task.domain.user.entity.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserRoleResponse {
        private String username;
        private String email;
        private List<String> roles;

        public static UpdateUserRoleResponse of (User user) {
                return UpdateUserRoleResponse.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(List.of(user.getUserRole().name()))
                        .build();
        }
}
