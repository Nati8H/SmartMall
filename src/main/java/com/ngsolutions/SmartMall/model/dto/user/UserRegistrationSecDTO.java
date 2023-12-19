package com.ngsolutions.SmartMall.model.dto.user;

import com.ngsolutions.SmartMall.model.validation.FieldMatch;
import com.ngsolutions.SmartMall.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords should match."
)
public class UserRegistrationSecDTO {
    @NotEmpty(message = "Username should not be empty")
    String username;
    @NotNull(message = "Email should not be empty")
    @Email(message = "Not a valid email address")
    @UniqueUserEmail(message = "There is already a registered user with that email")
    String email;
    @Length(min = 6, max = 30, message = "Password should be at least 6 symbols")
    String password;
    String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public static UserRegistrationSecDTO empty() {
        return new UserRegistrationSecDTO();
    }
}
