package com.finance.dashboard.user.controller;

import com.finance.dashboard.common.dto.ApiResponse;
import com.finance.dashboard.user.dto.CreateUserRequest;
import com.finance.dashboard.user.dto.UpdateUserRequest;
import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User created successfully")
                .data(userService.createUser(request))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(userService.getAllUsers())
                .build());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User fetched successfully")
                .data(userService.getUserById(userId))
                .build());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId,
                                                                @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User updated successfully")
                .data(userService.updateUser(userId, request))
                .build());
    }

    @PatchMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateStatus(@PathVariable Long userId,
                                                          @RequestParam String status) {
        userService.updateUserStatus(userId, status);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("User status updated successfully")
                .data(null)
                .build());
    }
}
