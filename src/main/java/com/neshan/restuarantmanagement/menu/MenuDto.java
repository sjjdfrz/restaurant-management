package com.neshan.restuarantmanagement.menu;

import jakarta.validation.constraints.NotBlank;

public record MenuDto(

        int id,

        @NotBlank(message = "Invalid Title: Empty title!")
        String title,

        String description) {
}
