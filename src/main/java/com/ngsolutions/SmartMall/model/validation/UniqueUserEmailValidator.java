package com.ngsolutions.SmartMall.model.validation;

import com.ngsolutions.SmartMall.repo.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

  private final UserRepository userRepository;

  public UniqueUserEmailValidator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return userRepository
        .findByEmail(value)
        .isEmpty();
  }
}
