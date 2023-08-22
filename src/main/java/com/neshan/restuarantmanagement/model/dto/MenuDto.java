package com.neshan.restuarantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MenuDto(

        long id,

        @NotBlank(message = "Invalid Title: Empty title!")
        String title,

        String description) {
}
