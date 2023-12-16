package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddBindingDTO;
import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
import com.ngsolutions.SmartMall.model.entity.Category;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    CategoryDisplayDTO getById(long id);

    List<CategoryDisplayDTO> getAll();

    void add(CategoriesAddBindingDTO categoriesAddBindingDTO, UserDetails user) throws IOException;
}
