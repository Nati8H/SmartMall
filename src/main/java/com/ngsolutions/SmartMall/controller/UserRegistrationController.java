package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationDTO;
import com.ngsolutions.SmartMall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/users")
@Controller
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("login");
    }

    @PostMapping("/register")
    public ModelAndView register(UserRegistrationDTO userRegistrationDTO) {

        userService.registerUser(userRegistrationDTO);

        return new ModelAndView("profile-settings");
    }


}