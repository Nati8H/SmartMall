package com.ngsolutions.SmartMall.controller;

import com.ngsolutions.SmartMall.model.dto.order.OrderDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.service.OrderService;
import com.ngsolutions.SmartMall.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/shopping-cart/open")
    public ModelAndView viewShoppingCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);

        if (!model.containsAttribute("orderDTO")) {
            model.addAttribute("orderDTO", orderDTO);
        }

        model.addAttribute("products", this.orderService.getAllProductsForOrder(orderDTO.getId()));

        return new ModelAndView("shopping-cart");
    }

    @GetMapping("shopping-cart/make-order")
    public ModelAndView makeOrder(@AuthenticationPrincipal UserDetails userDetails){
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);
        this.orderService.finalizeOrder(orderDTO);
        return new ModelAndView("index");
    }

    @GetMapping("shopping-cart/remove-product/{id}")
    public String removeProduct(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails){
        OrderDTO orderDTO = this.orderService.getActiveOrderForUser(userDetails);
        ProductDisplayDTO product = this.productService.getProductDisplayDTOById(Long.parseLong(id));
        this.orderService.removeProductFromOrder(orderDTO, product);
        return "redirect:/shopping-cart/open";
    }
}
