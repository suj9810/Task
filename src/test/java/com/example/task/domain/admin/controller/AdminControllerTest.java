package com.example.task.domain.admin.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import com.example.task.common.jwt.TokenProvider;
import com.example.task.domain.admin.dto.response.UpdateUserRoleResponse;
import com.example.task.domain.admin.service.AdminService;
import com.example.task.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
@EnableMethodSecurity(prePostEnabled = true)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private TokenProvider tokenProvider;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("권한 업데이트 성공 - ADMIN 변경")
    void updateUserRole_success() throws Exception {
        // given
        Long userId = 1L;
        UpdateUserRoleResponse response = UpdateUserRoleResponse.builder()
                .username("testuser")
                .email("test@example.com")
                .roles(List.of("ROLE_ADMIN"))
                .build();

        given(adminService.updateUserRole(userId)).willReturn(response);

        // when, then
        mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200 OK"))
                .andExpect(jsonPath("$.message").value("권한 변경에 성공하였습니다."));
        }


    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("권한 업데이트 실패 - 권한 부족")
    void updateUserRole_fail_insufficientAuthority() throws Exception {
        // given
        Long userId = 1L;

        // when, then
        mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
