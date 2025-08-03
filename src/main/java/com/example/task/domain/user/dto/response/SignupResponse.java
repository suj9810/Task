package com.example.task.domain.user.dto.response;

import com.example.task.domain.user.entity.User;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public static SignupResponse of(User user) {
        return SignupResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(List.of(user.getUserRole().name()))
                .build();
    }
}
