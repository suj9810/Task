package com.example.task.domain.admin.controller;

import com.example.task.domain.admin.dto.response.UpdateUserRoleResponse;
import com.example.task.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/users/{userId}/roles")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping
    public ResponseEntity<UpdateUserRoleResponse> updateUserRoles(
            @PathVariable("userId") Long userId) {
        UpdateUserRoleResponse updateUserRoleResponse = adminService.updateUserRole(userId);
        return ResponseEntity.ok(updateUserRoleResponse);
    }

}
