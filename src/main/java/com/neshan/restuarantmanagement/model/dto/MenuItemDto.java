package com.neshan.restuarantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MenuItemDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

        @NotBlank(message = "Invalid Price: Empty price!")
        double price,

        String description

) {
}
