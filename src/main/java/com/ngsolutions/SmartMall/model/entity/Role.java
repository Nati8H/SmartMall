package com.ngsolutions.SmartMall.model.entity;

import com.ngsolutions.SmartMall.model.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
