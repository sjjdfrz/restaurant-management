package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restaurantmanagement.model.ItemStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

//        @Min(value = 10, message = "The discount must be between 10 and 90!")
//        @Max(value = 90, message = "The discount must be between 10 and 90!")
//        Integer discount,

        @NotNull(message = "Invalid Price: Empty price!")
        Integer price,

        String description,
        ItemStatus itemStatus
) {
}
