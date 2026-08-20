package com.folkify.admin.controller;

import com.folkify.admin.dto.AdminStatsResponse;
import com.folkify.admin.service.AdminService;
import com.folkify.auth.repository.UserRepository;
import com.folkify.auth.service.JwtService;
import com.folkify.config.SecurityConfig;
import com.folkify.exception.GlobalExceptionHandler;
import com.folkify.security.JwtAuthFilter;
import com.folkify.security.RestAccessDeniedHandler;
import com.folkify.security.RestAuthenticationEntryPoint;
import com.folkify.security.SecurityErrorResponder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Kiểm tra @PreAuthorize("hasRole('ADMIN')") trên /api/admin trả đúng 401/403/200. */
@WebMvcTest(AdminController.class)
@Import({SecurityConfig.class, JwtAuthFilter.class, GlobalExceptionHandler.class,
        SecurityErrorResponder.class, RestAuthenticationEntryPoint.class, RestAccessDeniedHandler.class})
class AdminControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    @Test
    void chuaDangNhap_tra401() throws Exception {
        mockMvc.perform(get("/api/admin/stats"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(4001));
    }

    @Test
    @WithMockUser(roles = "USER")
    void userThuong_tra403() throws Exception {
        mockMvc.perform(get("/api/admin/stats"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(4003));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void admin_tra200() throws Exception {
        Mockito.when(adminService.getStats())
                .thenReturn(new AdminStatsResponse(0, 0, 0, 0, 0, 0));

        mockMvc.perform(get("/api/admin/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000));
    }
}
