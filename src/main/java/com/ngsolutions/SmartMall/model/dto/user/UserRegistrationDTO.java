package com.ngsolutions.SmartMall.model.dto.user;

import com.ngsolutions.SmartMall.model.validation.FieldMatch;
import com.ngsolutions.SmartMall.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Passwords should match."
)
public record UserRegistrationDTO(@NotEmpty String username,
                                  @NotNull @Email @UniqueUserEmail String email,
                                  String password,
                                  String confirmPassword) {

}
