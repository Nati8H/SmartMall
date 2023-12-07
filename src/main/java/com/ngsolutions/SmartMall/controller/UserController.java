package com.ngsolutions.SmartMall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/edit-profile")
    public ModelAndView editProfile() {
        return new ModelAndView("edit-profile");
    }
}