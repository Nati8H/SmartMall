package com.ngsolutions.SmartMall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

    @GetMapping("/services")
    public ModelAndView services() {
        return new ModelAndView("services");
    }

    @GetMapping("/blog")
    public ModelAndView blog() {
        return new ModelAndView("products");
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        return new ModelAndView("contact");
    }

    @GetMapping("/blog-single")
    public ModelAndView blogSingle() {
        return new ModelAndView("blog-single");
    }
}
