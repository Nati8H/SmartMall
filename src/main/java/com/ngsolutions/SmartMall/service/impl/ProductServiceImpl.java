package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.model.entity.Category;
import com.ngsolutions.SmartMall.model.entity.Product;
import com.ngsolutions.SmartMall.repo.CategoryRepository;
import com.ngsolutions.SmartMall.repo.ProductRepository;
import com.ngsolutions.SmartMall.service.ProductService;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageEncryptor imageEncryptor;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ImageEncryptor imageEncryptor) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageEncryptor = imageEncryptor;
    }

    @Override
    public void add(ProductsAddBindingModel productsAddBindingModel) throws IOException {

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
    public void update(ProductsAddBindingModel productsAddBindingModel) throws IOException {
        if (productsAddBindingModel.getId() > 0){
            Product product = mapProductsAddBindingModelToProduct(productsAddBindingModel);
            if (product.getPhoto() == null){
                product.setPhoto(this.productRepository.findById(productsAddBindingModel.getId()).get().getPhoto());
            }
            productRepository.save(product);
        }
    }

    @Override
    public void delete(long id){
        productRepository.deleteById(id);
    }

    @Override
    public ProductsAddBindingModel getProductAddBindingModelById(long id) throws IOException{
        return productRepository.findById(id).map(x -> {
            try {
                return mapProductToProductsAddBindingModel(x);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).get();
    }

    @Override
    public ProductDisplayDTO getProductDisplayDTOById(long id){
        return productRepository.findById(id).map(this::mapProductToProductDisplayDTO).get();
    }

    @Override
    public Page<ProductDisplayDTO> getAllProductsByCategory(Long categoryId, Pageable pageable) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        List<ProductDisplayDTO> productDisplayDTOs = productRepository.findAll()
                .stream().filter(x -> x.getCategory().getId() == categoryId)
                .map(this::mapProductToProductDisplayDTO)
                .toList();

        return new PageImpl<>(productDisplayDTOs, PageRequest.ofSize(6), productDisplayDTOs.size());
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }

    public Product mapProductsAddBindingModelToProduct(ProductsAddBindingModel productsAddBindingModel) throws IOException {
        Product product = new Product();
        product.setId(productsAddBindingModel.getId());
        product.setName(productsAddBindingModel.getName());
        product.setCategory(this.categoryRepository.findById(productsAddBindingModel.getCategoryId()).get());
        if (productsAddBindingModel.getCurrencyCode() != null) {
            product.setCurrency(Currency.getInstance(productsAddBindingModel.getCurrencyCode()));
        }
        product.setDiscount(productsAddBindingModel.getDiscount());
        product.setDescription(productsAddBindingModel.getDescription());
        product.setPrice(productsAddBindingModel.getPrice());

        if (productsAddBindingModel.getPhoto() != null) {
            product.setPhoto(productsAddBindingModel.getPhoto().isEmpty() ? null : this.imageEncryptor.EncryptImage(productsAddBindingModel.getPhoto()));
        }

        return product;
    }

    @Override
    public ProductDisplayDTO mapProductToProductDisplayDTO(Product product) {
        ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();

        productDisplayDTO.setId(product.getId());
        productDisplayDTO.setName(product.getName());
        productDisplayDTO.setDescription(product.getDescription());
        productDisplayDTO.setPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(product.getPhoto()));
        productDisplayDTO.setPrice(product.getPrice());
        productDisplayDTO.setDiscount(product.getDiscount());
        if (product.getCategory() != null) {
            productDisplayDTO.setCategoryId(product.getCategory().getId());
            productDisplayDTO.setCategoryName(product.getCategory().getName());
        }
        if (product.getCurrency() != null) {
            productDisplayDTO.setCurrencyCode(product.getCurrency().getCurrencyCode());
        }

        return productDisplayDTO;
    }

    public ProductsAddBindingModel mapProductToProductsAddBindingModel(Product product) throws IOException {
        ProductsAddBindingModel productsAddBindingModel = new ProductsAddBindingModel();

        productsAddBindingModel.setId(product.getId());
        productsAddBindingModel.setName(product.getName());
        productsAddBindingModel.setDescription(product.getDescription());
        productsAddBindingModel.setPhoto(this.imageEncryptor.DecryptImageAsMultipartFile(product.getPhoto()));
        productsAddBindingModel.setDisplayPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(product.getPhoto()));
        productsAddBindingModel.setPrice(product.getPrice());
        productsAddBindingModel.setDiscount(product.getDiscount());
        if (product.getCategory() != null) {
            productsAddBindingModel.setCategoryId(product.getCategory().getId());
        }
        if (product.getCurrency() != null) {
            productsAddBindingModel.setCurrencyCode(product.getCurrency().getCurrencyCode());
        }

        return productsAddBindingModel;
    }
}
