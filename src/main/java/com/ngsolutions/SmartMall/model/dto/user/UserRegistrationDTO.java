package com.ngsolutions.SmartMall.model.dto.user;

import com.ngsolutions.SmartMall.model.dto.product.ProductsAddBindingModel;
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
public record UserRegistrationDTO(@NotEmpty(message = "Username should not be empty")
                                  String username,
                                  @NotNull(message = "Email should not be empty")
                                  @Email(message = "Not a valid email address")
                                  @UniqueUserEmail(message = "There is already a registered user with that email")
                                  String email,
                                  @Length(min = 6, max = 30, message = "Password should be at least 6 symbols")
                                  String password,
                                  String confirmPassword) {

}
