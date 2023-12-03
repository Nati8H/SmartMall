package com.ngsolutions.SmartMall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @GetMapping("/add-product")
    public ModelAndView addProduct() {
        return new ModelAndView("add-product");
    }
}
