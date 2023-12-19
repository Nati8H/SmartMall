package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddBindingDTO;
import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
import com.ngsolutions.SmartMall.model.dto.product.ProductDisplayDTO;
import com.ngsolutions.SmartMall.model.entity.Category;
import com.ngsolutions.SmartMall.model.entity.Product;
import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.repo.CategoryRepository;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.CategoryService;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ImageEncryptor imageEncryptor;

    public CategoryServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, ImageEncryptor imageEncryptor) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.imageEncryptor = imageEncryptor;
    }

    @Override
    public void add(CategoriesAddBindingDTO categoriesAddBindingDTO, UserDetails user) throws IOException {

        Category category = mapCategoriesAddBindingModelToCategory(categoriesAddBindingDTO);
        User userEntity = userRepository.findByEmail(user.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + user.getUsername() + " not found!"));

        category.setUser(userEntity);

        categoryRepository.save(category);
    }

    public CategoryDisplayDTO getById(long id){
        return mapCategoryToCategoryDisplayDTO(this.categoryRepository.findById(id).get());
    }

    public List<CategoryDisplayDTO> getAll(){
        return this.categoryRepository.findAll().stream().map(this::mapCategoryToCategoryDisplayDTO).toList();
    }

    public Category mapCategoriesAddBindingModelToCategory(CategoriesAddBindingDTO categoriesAddBindingDTO) throws IOException {
        Category category = new Category();
        category.setName(categoriesAddBindingDTO.getName());
        category.setDescription(categoriesAddBindingDTO.getDescription());

        category.setPhoto(
                this.imageEncryptor.EncryptImage(categoriesAddBindingDTO.getPhoto())
        );

        return category;
    }

    public CategoryDisplayDTO mapCategoryToCategoryDisplayDTO(Category category) {
        CategoryDisplayDTO categoryDisplayDTO = new CategoryDisplayDTO();

        categoryDisplayDTO.setId(category.getId());
        categoryDisplayDTO.setName(category.getName());
        categoryDisplayDTO.setDescription(category.getDescription());
        categoryDisplayDTO.setPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(category.getPhoto()));

        return categoryDisplayDTO;
    }
}
