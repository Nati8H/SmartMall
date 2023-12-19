package com.ngsolutions.SmartMall.model.entity;

import com.ngsolutions.SmartMall.model.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @NotNull
    @Column(unique = true)
    private int importance;

    public String getRoleName() {
        return role.toString();
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
