package com.example.task.domain.user.dto.response;

import com.example.task.domain.user.entity.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
    private String username;
    private String email;
    private List<RoleInfo> roles;

    @Getter
    @Builder
    public static class RoleInfo {
        private String role;
    }

    public static SignupResponse of(User user) {
        return SignupResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(List.of(RoleInfo.builder().role(user.getUserRole().name()).build()))
                .build();
    }
}
