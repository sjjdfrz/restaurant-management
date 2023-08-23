package com.neshan.restuarantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neshan.restuarantmanagement.model.entity.MenuItem;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MenuDto(

        long id,

        @NotBlank(message = "Invalid Title: Empty title!")
        String title,

        String description,

        List<MenuItem> menuItems

) {
}
