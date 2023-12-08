package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.model.entity.Product;
import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.repo.CategoryRepository;
import com.ngsolutions.SmartMall.repo.ProductRepository;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.ProductService;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Currency;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ImageEncryptor imageEncryptor;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, ImageEncryptor imageEncryptor) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.imageEncryptor = imageEncryptor;
    }

    @Override
    public void add(String username, ProductsAddBindingModel productsAddBindingModel) throws IOException {

        User user = null;
        if (username != null) {
            user = userRepository.findByUsername(username);
        }

        if (productsAddBindingModel != null) {
//            ModelMapper modelMapper = new ModelMapper();
//            TypeMap<ProductsAddBindingModel, Product> typeMap = modelMapper.createTypeMap(ProductsAddBindingModel.class, Product.class);
//            typeMap.addMappings(mapper -> {
//                mapper.map(src -> {
//                    try {
//                        return this.imageEncryptor.EncryptImage(src.getPhoto());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }, Product::setPhoto);
//            });
//            Product product  = modelMapper.map(productsAddBindingModel, Product.class);

            Product product = mapProductsAddBindingModelToProduct(productsAddBindingModel);
            productRepository.save(product);
        }
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void removeAll() {
        productRepository.deleteAll();
    }

    public Product mapProductsAddBindingModelToProduct(ProductsAddBindingModel productsAddBindingModel) throws IOException {
        Product product = new Product();
        product.setName(productsAddBindingModel.getName());
        product.setCategory(this.categoryRepository.findById(productsAddBindingModel.getCategoryId()).get());
        product.setCurrency(Currency.getInstance(productsAddBindingModel.getCurrencyCode()));
        product.setDiscount(productsAddBindingModel.getDiscount());
        product.setPrice(productsAddBindingModel.getPrice());

        product.setPhoto(
                this.imageEncryptor.EncryptImage(productsAddBindingModel.getPhoto())
        );

        return product;
    }
}
