package com.finance.dashboard.user.service;

import com.finance.dashboard.user.dto.CreateUserRequest;
import com.finance.dashboard.user.dto.UpdateUserRequest;
import com.finance.dashboard.user.dto.UserResponse;
import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long userId);

    UserResponse updateUser(Long userId, UpdateUserRequest request);

    void updateUserStatus(Long userId, String status);
}
