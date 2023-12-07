package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.service.CategoryService;
import com.ngsolutions.SmartMall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/add")
    public ModelAndView addProduct(Model model) {
        if (!model.containsAttribute("productsAddBindingModel")) {
            model.addAttribute("productsAddBindingModel", ProductsAddBindingModel.empty());
        }

        model.addAttribute("categories", categoryService.getAll());
        return new ModelAndView("add-product");
    }

    @PostMapping("/products/add")
    public ModelAndView add(
            @ModelAttribute("productsAddBindingModel") @Valid ProductsAddBindingModel productsAddBindingModel,
            BindingResult bindingResult) throws IOException {
//        if (!loggedUser.isLogged()) {
//            return new ModelAndView("redirect:/");
//        }

        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-product");
        }
//        wordService.add(loggedUser.getUsername(), productsAddBindingModel);
        productService.add("nati", productsAddBindingModel);

        return new ModelAndView("index");
    }
}
