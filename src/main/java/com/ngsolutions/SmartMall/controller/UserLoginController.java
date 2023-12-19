package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.user.UserRegistrationSecDTO;
import com.ngsolutions.SmartMall.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserLoginController {

    @GetMapping("/users/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/users/login-error")
    public String onFailure(
            @ModelAttribute("email") String email,
            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return "login";
    }
}