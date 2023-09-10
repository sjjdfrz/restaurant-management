package com.neshan.restaurantmanagement.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(0), ADMIN(1);

    private final int code;
}
