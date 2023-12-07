package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationDTO;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);
}
