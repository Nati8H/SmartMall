package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.event.UserRegisteredEvent;
import com.ngsolutions.SmartMall.service.EmailService;
import com.ngsolutions.SmartMall.service.UserActivationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final EmailService emailService;

    public UserActivationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUsername());
    }
}
