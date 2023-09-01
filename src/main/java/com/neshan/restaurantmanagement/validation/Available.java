package com.neshan.restaurantmanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = AvailableValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Available {

    String message() default "Your selected item is not available!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
