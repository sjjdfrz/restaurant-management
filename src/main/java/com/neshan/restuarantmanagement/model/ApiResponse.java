package com.neshan.restuarantmanagement.model;

import lombok.*;

@Builder
public record ApiResponse<T>(String status, String message, T data) {
}
