package com.springboot.task.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.springboot.task.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;

public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {

    @Autowired
    FileService userRepository;

    @Override
    public void initialize(UniquePhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (userRepository==null){
            return true;
        }
        return !userRepository.isPhoneNumberPresent(value);

    }
}