package com.neshan.restuarantmanagement;

import lombok.*;

@Builder
public record ApiResponse<T>(String status, String message, T data) {
}
