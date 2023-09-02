package com.neshan.restaurantmanagement.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final String status = "fail";
    private final String message;
    private final long timestamp;
    private List<ValidationError> validationErrors;

    private record ValidationError(String field, String message) {
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(validationErrors)) {
            validationErrors = new ArrayList<>();
        }
        validationErrors.add(new ValidationError(field, message));
    }
}