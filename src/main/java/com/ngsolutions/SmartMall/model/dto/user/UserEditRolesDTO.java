package com.ngsolutions.SmartMall.model.dto.user;

import com.ngsolutions.SmartMall.model.entity.Role;

import java.util.List;

public class UserEditRolesDTO {

    private long Id;

    private String username;

    private List<Role> roles;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean containsRoleId(long id){
        for (Role role : roles) {
            if (role.getId() == id){
                return true;
            }
        }
        return false;
    }
}
