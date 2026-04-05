package com.finance.dashboard.user.service.impl;

import com.finance.dashboard.common.exception.BadRequestException;
import com.finance.dashboard.common.exception.ResourceNotFoundException;
import com.finance.dashboard.user.dto.CreateUserRequest;
import com.finance.dashboard.user.dto.UpdateUserRequest;
import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.entity.Role;
import com.finance.dashboard.user.entity.User;
import com.finance.dashboard.user.enums.UserStatus;
import com.finance.dashboard.user.mapper.UserMapper;
import com.finance.dashboard.user.repository.UserRepository;
import com.finance.dashboard.user.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(request.getStatus())
                .roles(request.getRoles().stream()
                        .map(roleName -> Role.builder().name(roleName).build())
                        .collect(Collectors.toSet()))
                .build();

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return userMapper.toResponse(findUserById(userId));
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = findUserById(userId);

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());
        user.setRoles(request.getRoles().stream()
                .map(roleName -> Role.builder().name(roleName).build())
                .collect(Collectors.toSet()));

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public void updateUserStatus(Long userId, String status) {
        User user = findUserById(userId);

        try {
            user.setStatus(UserStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid status. Allowed values: ACTIVE, INACTIVE");
        }

        userRepository.save(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
