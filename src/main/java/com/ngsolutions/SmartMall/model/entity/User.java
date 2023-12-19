package com.ngsolutions.SmartMall.model.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Length(min = 3, max = 20)
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    @Column
    private String shopName;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] shopPhoto;

    @Column
    private boolean isActive;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public User setCategories(List<Category> categories) {
        this.categories = categories;
        return this;
    }

    public String getShopName() {
        return shopName;
    }

    public User setShopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public byte[] getShopPhoto() {
        return shopPhoto;
    }

    public User setShopPhoto(byte[] shopPhoto) {
        this.shopPhoto = shopPhoto;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }
}
