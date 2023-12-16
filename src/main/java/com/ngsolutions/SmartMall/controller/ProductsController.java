package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.service.CategoryService;
import com.ngsolutions.SmartMall.service.CurrencyService;
import com.ngsolutions.SmartMall.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductsController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CurrencyService currencyService;

    public ProductsController(ProductService productService, CategoryService categoryService, CurrencyService currencyService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
    }

    @GetMapping("products/all/{id}")
    public ModelAndView allByCategoryId(@PathVariable String id, Model model,
                                        @PageableDefault(
                              size = 3,
                              sort = "price"
                      ) Pageable pageable) {

        Page<ProductDisplayDTO> allProductsByCategoryId = productService.getAllProductsByCategory(Long.parseLong(id), pageable);
        List<CategoryDisplayDTO> allCategories = categoryService.getAll();
        CategoryDisplayDTO category = categoryService.getById(Long.parseLong(id));

        model.addAttribute("category" ,category);
        model.addAttribute("categories" ,allCategories);
        model.addAttribute("products", allProductsByCategoryId);

        return new ModelAndView("products");
    }
}
