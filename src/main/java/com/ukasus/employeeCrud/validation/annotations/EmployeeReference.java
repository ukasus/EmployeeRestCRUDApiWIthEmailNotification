package com.ukasus.employeeCrud.validation.annotations;

import com.ukasus.employeeCrud.validation.validators.EmployeeReferenceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = EmployeeReferenceValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmployeeReference {
    String message() default "Must be a valid employee id that can be used as reportsTo value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}