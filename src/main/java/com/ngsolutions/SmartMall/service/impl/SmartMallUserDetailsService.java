package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.entity.Role;
import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SmartMallUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SmartMallUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(SmartMallUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private static UserDetails map(User userEntity) {
        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(SmartMallUserDetailsService::map).toList())
                .build();
    }

    private static GrantedAuthority map(Role roleEntity) {
        return new SimpleGrantedAuthority(
                "ROLE_" + roleEntity.getRole().name()
        );
    }
}