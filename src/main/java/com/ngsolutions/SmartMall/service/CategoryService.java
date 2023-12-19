package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddDTO;
import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    void update(CategoriesAddDTO categoriesAddDTO, UserDetails user) throws IOException;

    void delete(long id);

    CategoryDisplayDTO getById(long id);

    CategoryDisplayDTO getFirstAvailableCategory();

    CategoriesAddDTO getCategoriesAddDTOById(long id);

    List<CategoryDisplayDTO> getAll();

    void add(CategoriesAddDTO categoriesAddDTO, UserDetails user) throws IOException;
}
