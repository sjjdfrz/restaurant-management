package com.neshan.restaurantmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantsDto {

    private long id;

    @NotBlank(message = "Invalid Name: Empty name!")
    private String name;

    @NotNull(message = "Invalid Telephone: Empty telephone!")
    private Long telephone;

    @NotBlank(message = "Invalid Address: Empty address!")
    private String address;
}
