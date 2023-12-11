package com.ngsolutions.SmartMall.model.dto.category;

import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
import org.springframework.web.multipart.MultipartFile;

public class CategoriesAddBindingDTO {

    private String name;

    private String description;

    private MultipartFile photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public static CategoriesAddBindingDTO empty() {
        return new CategoriesAddBindingDTO();
    }
}
