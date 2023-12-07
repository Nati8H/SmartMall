package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationDTO;
import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.model.event.UserRegisteredEvent;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher appEventPublisher;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ApplicationEventPublisher appEventPublisher) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
    }

    @Override
    public void registerUser(
            UserRegistrationDTO userRegistrationDTO) {

        userRepository.save(map(userRegistrationDTO));

        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",
                userRegistrationDTO.email(),
                userRegistrationDTO.username()
        ));
    }

    private User map(UserRegistrationDTO userRegistrationDTO) {
        return new User()
                .setActive(false)
                .setUsername(userRegistrationDTO.username())
                .setEmail(userRegistrationDTO.email())
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
    }
}
