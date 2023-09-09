package com.neshan.restaurantmanagement.validation;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.model.ItemStatus;
import com.neshan.restaurantmanagement.model.dto.CartItemRequestDto;
import com.neshan.restaurantmanagement.model.entity.Item;
import com.neshan.restaurantmanagement.repository.ItemRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AvailableValidator implements ConstraintValidator<Available, List<CartItemRequestDto>> {

    private ItemRepository itemRepository;

    @Override
    public boolean isValid(List<CartItemRequestDto> cartItems, ConstraintValidatorContext constraintValidatorContext) {

        return cartItems
                .stream()
                .allMatch(cartItem -> {

                    Item item = itemRepository
                            .findById(cartItem.getItemId())
                            .orElseThrow(() -> new NoSuchElementFoundException(
                                    String.format("The item with ID %d was not found.", cartItem.getItemId())));

                    return item.getItemStatus() == ItemStatus.AVAILABLE;
                });
    }
}
