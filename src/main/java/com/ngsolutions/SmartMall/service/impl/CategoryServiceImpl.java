package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.entity.Category;
import com.ngsolutions.SmartMall.repo.CategoryRepository;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.CategoryService;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.springframework.stereotype.Service;

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

    public List<Category> getAll(){
        return this.categoryRepository.findAll();
    }
}
