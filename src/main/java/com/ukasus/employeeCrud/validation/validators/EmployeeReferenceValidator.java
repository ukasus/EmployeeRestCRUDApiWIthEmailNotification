package com.ukasus.employeeCrud.validation.validators;

import com.ukasus.employeeCrud.context.ApplicationContextProvider;
import com.ukasus.employeeCrud.repositories.EmployeeRepository;
import com.ukasus.employeeCrud.validation.annotations.EmployeeReference;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeReferenceValidator implements ConstraintValidator<EmployeeReference, String> {

    private EmployeeRepository employeeRepository = ApplicationContextProvider.getBean(EmployeeRepository.class);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !employeeRepository.findById(value).isEmpty();
    }
}