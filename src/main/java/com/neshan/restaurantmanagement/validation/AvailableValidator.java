package com.neshan.restaurantmanagement.validation;

import com.neshan.restaurantmanagement.model.ItemStatus;
import com.neshan.restaurantmanagement.model.entity.CartItem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class AvailableValidator implements ConstraintValidator<Available, List<CartItem>> {
    @Override
    public boolean isValid(List<CartItem> cartItems, ConstraintValidatorContext constraintValidatorContext) {

        return cartItems
                .stream()
                .allMatch(cartItem -> cartItem.getItem().getItemStatus() == ItemStatus.AVAILABLE);

    }
}
