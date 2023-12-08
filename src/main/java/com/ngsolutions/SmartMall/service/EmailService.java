package com.ngsolutions.SmartMall.service;

public interface EmailService {

    void sendRegistrationEmail(String email, String username, String activationCode);
}
