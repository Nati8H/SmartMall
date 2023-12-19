package com.ngsolutions.SmartMall.model.dto.user;

import com.ngsolutions.SmartMall.model.entity.Category;
import com.ngsolutions.SmartMall.model.entity.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserEditDTO {

    private long Id;

    private String username;

    private String password;

    private String matchPassword;

    private String newPassword;

    private String confirmNewPassword;

    private String email;

    private List<Role> roles;

    private String phoneNumber;

    private String address;

    private List<Category> categories;

    private String shopName;

    private MultipartFile shopPhoto;

    private String displayShopPhoto;

    private boolean isActive;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchPassword() {
        return matchPassword;
    }

    public void setMatchPassword(String matchPassword) {
        this.matchPassword = matchPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public MultipartFile getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(MultipartFile shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getDisplayShopPhoto() {
        return displayShopPhoto;
    }

    public void setDisplayShopPhoto(String displayShopPhoto) {
        this.displayShopPhoto = displayShopPhoto;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Role getMainRole(){
        roles.sort(Comparator.comparing(Role::getImportance));
        return (roles == null || roles.isEmpty()) ? null : roles.get(0);
    }
}
