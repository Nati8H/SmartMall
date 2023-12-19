package com.ngsolutions.SmartMall.model.dto.category;

import org.springframework.web.multipart.MultipartFile;

public class CategoriesAddDTO {

    private long id;

    private String name;

    private String description;

    private MultipartFile photo;

    private String displayPhoto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getDisplayPhoto() {
        return displayPhoto;
    }

    public void setDisplayPhoto(String displayPhoto) {
        this.displayPhoto = displayPhoto;
    }

    public static CategoriesAddDTO empty() {
        return new CategoriesAddDTO();
    }
}
