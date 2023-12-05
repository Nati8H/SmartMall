package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;

import java.io.IOException;

public interface ProductService {
    void add(String username, ProductsAddBindingModel wordsAddBindingModel) throws IOException;

    void remove(Long id);

    void removeAll();
}
