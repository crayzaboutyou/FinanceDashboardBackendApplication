package com.finance.dashboard.user.dto;

import com.finance.dashboard.user.enums.RoleName;
import com.finance.dashboard.user.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Status is required")
    private UserStatus status;

    @NotEmpty(message = "At least one role is required")
    private Set<RoleName> roles;
}
