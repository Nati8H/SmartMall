package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddDTO;
import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
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
    public void add(CategoriesAddDTO categoriesAddDTO, UserDetails user) throws IOException {

        Category category = mapCategoriesAddDTOToCategory(categoriesAddDTO);
        User userEntity = userRepository.findByEmail(user.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User with email " + user.getUsername() + " not found!"));

        category.setUser(userEntity);

        categoryRepository.save(category);
    }

    @Override
    public void update(CategoriesAddDTO categoriesAddDTO, UserDetails user) throws IOException {
        Category oldCategory = this.categoryRepository.findById(categoriesAddDTO.getId()).get();
        Category category = mapCategoriesAddDTOToCategory(categoriesAddDTO);
        category.setUser(oldCategory.getUser());

        if (category.getPhoto() == null){
            category.setPhoto(oldCategory.getPhoto());
        }

        categoryRepository.save(category);
    }

    @Override
    public void delete(long id){
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDisplayDTO getById(long id){
        return mapCategoryToCategoryDisplayDTO(this.categoryRepository.findById(id).get());
    }

    @Override
    public CategoryDisplayDTO getFirstAvailableCategory(){
        return mapCategoryToCategoryDisplayDTO(this.categoryRepository.findAll().get(0));
    }

    @Override
    public CategoriesAddDTO getCategoriesAddDTOById(long id){
        return mapCategoryToCategoriesAddDTO(this.categoryRepository.findById(id).get());
    }

    @Override
    public List<CategoryDisplayDTO> getAll(){
        return this.categoryRepository.findAll().stream().map(this::mapCategoryToCategoryDisplayDTO).toList();
    }

    public Category mapCategoriesAddDTOToCategory(CategoriesAddDTO categoriesAddDTO) throws IOException {
        Category category = new Category();
        category.setId(categoriesAddDTO.getId());
        category.setName(categoriesAddDTO.getName());
        category.setDescription(categoriesAddDTO.getDescription());

        category.setPhoto(categoriesAddDTO.getPhoto().isEmpty() ? null : this.imageEncryptor.EncryptImage(categoriesAddDTO.getPhoto()));


        return category;
    }

    public CategoriesAddDTO mapCategoryToCategoriesAddDTO(Category category) {
        CategoriesAddDTO categoriesAddDTO = new CategoriesAddDTO();

        categoriesAddDTO.setId(category.getId());
        categoriesAddDTO.setName(category.getName());
        categoriesAddDTO.setDescription(category.getDescription());
        categoriesAddDTO.setDisplayPhoto("data:image/png;base64," + this.imageEncryptor.DecryptImage(category.getPhoto()));

        return categoriesAddDTO;
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
