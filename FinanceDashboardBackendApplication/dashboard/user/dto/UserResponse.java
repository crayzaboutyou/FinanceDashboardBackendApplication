package com.finance.dashboard.user.dto;

import com.finance.dashboard.user.enums.RoleName;
import com.finance.dashboard.user.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private UserStatus status;
    private Set<RoleName> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
