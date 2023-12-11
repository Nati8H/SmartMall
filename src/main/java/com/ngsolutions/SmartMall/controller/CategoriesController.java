package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddBindingDTO;
import com.ngsolutions.SmartMall.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/add")
    public ModelAndView add(Model model) {
        if (!model.containsAttribute("categoriesAddBindingModel")) {
            model.addAttribute("categoriesAddBindingModel", CategoriesAddBindingDTO.empty());
        }

        return new ModelAndView("add-category");
    }

    @PostMapping("/categories/add")
    public ModelAndView add(
            @ModelAttribute("categoriesAddBindingModel") @Valid CategoriesAddBindingDTO categoriesAddBindingDTO,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-category");
        }

        categoryService.add(categoriesAddBindingDTO, userDetails);

        return new ModelAndView("index");
    }
}
