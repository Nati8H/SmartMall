package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.service.CategoryService;
import com.ngsolutions.SmartMall.service.CurrencyService;
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
import java.util.Currency;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CurrencyService currencyService;

    public ProductController(ProductService productService, CategoryService categoryService, CurrencyService currencyService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

    @GetMapping("/products/add")
    public ModelAndView addProduct(Model model) {
        if (!model.containsAttribute("productsAddBindingModel")) {
            model.addAttribute("productsAddBindingModel", ProductsAddBindingModel.empty());
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("currencies", currencyService.getAll());
        return new ModelAndView("add-product");
    }

    @PostMapping("/products/add")
    public ModelAndView add(
            @ModelAttribute("productsAddBindingModel") @Valid ProductsAddBindingModel productsAddBindingModel,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-product");
        }
        productService.add(productsAddBindingModel);

        return new ModelAndView("index");
    }
}
