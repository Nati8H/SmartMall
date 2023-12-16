package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ProductService {
    void add(ProductsAddBindingModel wordsAddBindingModel) throws IOException;

    void update(ProductsAddBindingModel productsAddBindingModel) throws IOException;

    void delete(long id);

    ProductsAddBindingModel getProductAddBindingModelById(long id) throws IOException;

    Page<ProductDisplayDTO> getAllProductsByCategory(Long categoryId, Pageable pageable);

    void remove(Long id);

    void removeAll();
}
