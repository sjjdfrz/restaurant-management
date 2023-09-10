package com.neshan.restaurantmanagement.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PREPARING(0), SENDING(1), COMPLETED(2), CANCELED(3);

    private final int code;
}
