package com.neshan.restaurantmanagement.model.enums.convertor;

import com.neshan.restaurantmanagement.model.enums.ItemStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ItemStatusConvertor implements AttributeConverter<ItemStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ItemStatus itemStatus) {
        if (itemStatus == null) {
            return null;
        }
        return itemStatus.getCode();
    }

    @Override
    public ItemStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Stream.of(ItemStatus.values())
                .filter(c -> c.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
