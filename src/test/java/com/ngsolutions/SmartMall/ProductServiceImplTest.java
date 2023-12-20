package com.ngsolutions.SmartMall;

import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import com.ngsolutions.SmartMall.model.entity.Category;
import com.ngsolutions.SmartMall.model.entity.Product;
import com.ngsolutions.SmartMall.repo.CategoryRepository;
import com.ngsolutions.SmartMall.repo.ProductRepository;
import com.ngsolutions.SmartMall.service.impl.ProductServiceImpl;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageEncryptor imageEncryptor;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() throws IOException {
        ProductsAddBindingModel bindingModel = new ProductsAddBindingModel();
        bindingModel.setName("Test Product");
        bindingModel.setCategoryId(1L);
        bindingModel.setCurrencyCode("USD"); // Provide a non-null currency code

        // Set a non-null MultipartFile for the photo
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "photo", "test.jpg", "image/jpeg", "Mock photo content".getBytes());
        bindingModel.setPhoto(mockMultipartFile);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(new Product());

        productService.add(bindingModel);

        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(Product.class));
    }



    @Test
    void testUpdate() {
        // Create a test ProductsAddBindingModel with a non-null CurrencyCode
        ProductsAddBindingModel bindingModel = new ProductsAddBindingModel();
        bindingModel.setName("Updated Product");
        bindingModel.setCategoryId(1L);
        bindingModel.setCurrencyCode("USD"); // Set a non-null currency code

        // Mock the necessary repository behavior if needed

        // Call the service method
        assertDoesNotThrow(() -> productService.update(bindingModel));

        // Add additional assertions if needed
    }

    @Test
    public void testDelete() {
        productService.delete(1L);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(Mockito.eq(1L));
    }

    @Test
    void testGetProductAddBindingModelById() throws IOException {
        // Mock a Product with a non-null Category
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);

        // Mock repository behavior
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Call the service method
        Optional<ProductsAddBindingModel> bindingModelOptional = Optional.ofNullable(productService.getProductAddBindingModelById(1L));

        // Assertions
        assertTrue(bindingModelOptional.isPresent());
        ProductsAddBindingModel bindingModel = bindingModelOptional.get();
        assertEquals(product.getName(), bindingModel.getName());
        assertEquals(product.getCategory().getId(), bindingModel.getCategoryId());
    }

    @Test
    public void testGetProductDisplayDTOById() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        when(imageEncryptor.DecryptImage(Mockito.any())).thenReturn(null);

        ProductDisplayDTO result = productService.getProductDisplayDTOById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetAllProductsByCategory() {
        Long categoryId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Category category = new Category();
        category.setId(categoryId);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setCategory(category);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        when(imageEncryptor.DecryptImage(Mockito.any())).thenReturn(null);

        Page<ProductDisplayDTO> result = productService.getAllProductsByCategory(categoryId, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals(2L, result.getContent().get(1).getId());
    }

    @Test
    public void testDeleteAll() {
        productService.deleteAll();

        Mockito.verify(productRepository, Mockito.times(1)).deleteAll();
    }

    @Test
    public void testMapProductToProductDisplayDTO() {
        Product product = new Product();
        product.setId(1L);

        when(imageEncryptor.DecryptImage(Mockito.any())).thenReturn(null);

        ProductDisplayDTO result = productService.mapProductToProductDisplayDTO(product);

        assertEquals(1L, result.getId());
    }

    @Test
    public void testMapProductsAddBindingModelToProduct() throws IOException {
        ProductsAddBindingModel bindingModel = new ProductsAddBindingModel();
        bindingModel.setName("Test Product");
        bindingModel.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(category));
        when(imageEncryptor.EncryptImage(Mockito.any())).thenReturn(null);

        Product result = productService.mapProductsAddBindingModelToProduct(bindingModel);

        assertEquals("Test Product", result.getName());
        assertEquals(1L, result.getCategory().getId());
    }

    @Test
    public void testMapProductToProductsAddBindingModel() throws IOException {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(imageEncryptor.DecryptImageAsMultipartFile(Mockito.any())).thenReturn(null);
        when(imageEncryptor.DecryptImage(Mockito.any())).thenReturn(null);

        ProductsAddBindingModel result = productService.mapProductToProductsAddBindingModel(product);

        assertEquals(1L, result.getId());
        assertEquals("Test Product", result.getName());
    }

}

