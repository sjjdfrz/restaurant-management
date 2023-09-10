package com.neshan.restaurantmanagement.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {

    AVAILABLE(0), UNAVAILABLE(1);

    private final int code;
}
