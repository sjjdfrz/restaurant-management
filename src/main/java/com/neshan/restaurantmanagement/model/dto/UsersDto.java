package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDto {

    private long id;

    @NotBlank(message = "Invalid firstName: Empty firstname!")
    private String firstName;

    @NotBlank(message = "Invalid lastName: Empty lastname!")
    private String lastName;

    @NotBlank(message = "Invalid Email: Empty email!")
    @Email
    private String email;
}
