package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.event.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);
}
