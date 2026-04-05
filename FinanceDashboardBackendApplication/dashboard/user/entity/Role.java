package com.finance.dashboard.user.entity;

import com.finance.dashboard.user.enums.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Role {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private RoleName name;
}
