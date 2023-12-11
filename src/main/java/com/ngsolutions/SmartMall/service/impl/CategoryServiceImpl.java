package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddBindingDTO;
import com.ngsolutions.SmartMall.model.entity.Category;
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

    public List<Category> getAll(){
        return this.categoryRepository.findAll();
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
}
