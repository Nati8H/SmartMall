package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.entity.Role;
import com.ngsolutions.SmartMall.repo.RoleRepository;
import com.ngsolutions.SmartMall.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
