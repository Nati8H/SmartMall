package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    void createUserIfNotExist(String email, String names);

    Authentication login(String email);
}
