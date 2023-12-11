package com.ngsolutions.SmartMall.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] photo;

    @ManyToOne
    @NotNull
    private User user;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
