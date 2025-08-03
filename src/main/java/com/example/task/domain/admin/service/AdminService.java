package com.example.task.domain.admin.service;

import com.example.task.common.code.ErrorCode;
import com.example.task.common.exception.CustomException;
import com.example.task.domain.admin.dto.response.UpdateUserRoleResponse;
import com.example.task.domain.user.entity.User;
import com.example.task.domain.user.entity.UserRole;
import com.example.task.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public UpdateUserRoleResponse updateUserRole(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updateUserRole(UserRole.ROLE_ADMIN);

        return UpdateUserRoleResponse.of(user);
    }
}
