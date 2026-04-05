package com.finance.dashboard.auth.dto;

import com.finance.dashboard.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private final String token;
    private final UserResponse user;
}
