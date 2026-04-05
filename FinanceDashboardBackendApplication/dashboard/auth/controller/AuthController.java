package com.finance.dashboard.auth.controller;

import com.finance.dashboard.auth.dto.AuthResponse;
import com.finance.dashboard.auth.dto.LoginRequest;
import com.finance.dashboard.auth.service.AuthService;
import com.finance.dashboard.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .data(authService.login(request))
                .build());
    }
}
