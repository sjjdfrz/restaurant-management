package com.neshan.restuarantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(

        long id,

        @NotBlank(message = "Invalid Name: Empty name!")
        String name,

        @Pattern(regexp = "09[0-9]{9}")
        long phone,

        @NotBlank(message = "Invalid Email: Empty email!")
        @Email
        String email

) {
}
