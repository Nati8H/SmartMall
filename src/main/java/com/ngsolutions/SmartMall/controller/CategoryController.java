package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddDTO;
import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/add")
    public ModelAndView add(Model model) {
        if (!model.containsAttribute("categoriesAddDTO")) {
            model.addAttribute("categoriesAddDTO", CategoriesAddDTO.empty());
        }

        model.addAttribute("action", "/categories/add");

        return new ModelAndView("add-category");
    }

    @PostMapping("/categories/add")
    public ModelAndView add(
            @ModelAttribute("categoriesAddDTO") @Valid CategoriesAddDTO categoriesAddDTO,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("add-category");
        }

        categoryService.add(categoriesAddDTO, userDetails);

        return new ModelAndView("index");
    }

    @GetMapping("/categories/edit/{id}")
    public ModelAndView editCategory(@PathVariable String id, Model model) throws IOException {
        CategoriesAddDTO category = categoryService.getCategoriesAddDTOById(Long.parseLong(id));

        if (!model.containsAttribute("categoriesAddDTO")) {
            model.addAttribute("categoriesAddDTO", category);
        }

        model.addAttribute("action", "/categories/edit/{id}(id=" + id + ")");
        return new ModelAndView("add-category");
    }

    @PostMapping("/categories/edit/{id}")
    public String editCategory(
            @ModelAttribute("categoriesAddDTO") @Valid CategoriesAddDTO categoriesAddDTO,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) throws IOException {

        if (bindingResult.hasErrors()) {
            return ("redirect:/categories/edit/" + categoriesAddDTO.getId());
        }
        categoryService.update(categoriesAddDTO, userDetails);

        return ("redirect:/products/all/" + categoriesAddDTO.getId());
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable String id) throws IOException {

        categoryService.delete(Long.parseLong(id));

        return ("redirect:/products/all/" + categoryService.getFirstAvailableCategory().getId());
    }

    @GetMapping("/categories/all")
    public ModelAndView all(Model model) {
        List<CategoryDisplayDTO> allCategories = categoryService.getAll();

        model.addAttribute("categories" ,allCategories);

        return new ModelAndView("fragments/sidebar");
    }
}
