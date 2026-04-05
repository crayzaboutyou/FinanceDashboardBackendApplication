package com.finance.dashboard.user.mapper;

import com.finance.dashboard.user.dto.UserResponse;
import com.finance.dashboard.user.entity.Role;
import com.finance.dashboard.user.entity.User;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
