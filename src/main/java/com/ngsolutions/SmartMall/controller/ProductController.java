package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.order.OrderDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.service.CategoryService;
import com.ngsolutions.SmartMall.service.CurrencyService;
import com.ngsolutions.SmartMall.service.OrderService;
import com.ngsolutions.SmartMall.service.ProductService;
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

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CurrencyService currencyService;
    private final OrderService orderService;

    public ProductController(ProductService productService, CategoryService categoryService, CurrencyService currencyService, OrderService orderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
        this.orderService = orderService;
    }

    @GetMapping("/products/add")
    public ModelAndView addProduct(Model model) {
        if (!model.containsAttribute("productsAddBindingModel")) {
            model.addAttribute("productsAddBindingModel", ProductsAddBindingModel.empty());
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("action", "/products/add");
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

    @GetMapping("/products/edit/{id}")
    public ModelAndView editProduct(@PathVariable String id, Model model) throws IOException {
        ProductsAddBindingModel product = productService.getProductAddBindingModelById(Long.parseLong(id));

        if (!model.containsAttribute("productsAddBindingModel")) {
            model.addAttribute("productsAddBindingModel", product);
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("currencies", currencyService.getAll());
        model.addAttribute("action", "/products/edit/{id}(id=" + id + ")");
        return new ModelAndView("add-product");
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(
            @ModelAttribute("productsAddBindingModel") @Valid ProductsAddBindingModel productsAddBindingModel,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ("redirect:/products/edit/" + productsAddBindingModel.getId());
        }
        productService.update(productsAddBindingModel);

        return ("redirect:/products/all/" + productsAddBindingModel.getCategoryId());
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable String id) throws IOException {

        long categoryId = productService.getProductAddBindingModelById(Long.parseLong(id)).getCategoryId();

        productService.delete(Long.parseLong(id));

        return ("redirect:/products/all/" + categoryId);
    }

    @GetMapping("/products/add-to-shopping-cart/{id}")
    public String addProductToShoppingCart(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails) {
        ProductDisplayDTO product = this.productService.getProductDisplayDTOById(Long.parseLong(id));
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);

        this.orderService.addProductToOrder(orderDTO, product);

        return ("redirect:/products/all/" + product.getCategoryId());
    }
}
