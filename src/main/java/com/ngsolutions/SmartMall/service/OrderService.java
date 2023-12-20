package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.order.OrderDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {
    void addOrder(OrderDTO orderDTO, UserDetails user);

    void addProductToOrder(OrderDTO orderDTO, ProductDisplayDTO productDisplayDTO);

    void removeProductFromOrder(OrderDTO orderDTO, ProductDisplayDTO productDisplayDTO);

    void removeAllProductFromOrder(OrderDTO orderDTO);

    void finalizeOrder(OrderDTO orderDTO);

    OrderDTO getOrderDTOById(long id);

    List<ProductDisplayDTO> getAllProductsForOrder(long id);

    OrderDTO getActiveOrderForUser(UserDetails userDetails);
}
