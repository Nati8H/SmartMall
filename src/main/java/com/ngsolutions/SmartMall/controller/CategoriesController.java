package com.ngsolutions.SmartMall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoriesController {

    @GetMapping("/categories/add")
    public ModelAndView addCategory() {
        return new ModelAndView("add-category");
    }
}
