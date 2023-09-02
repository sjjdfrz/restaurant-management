package com.neshan.restaurantmanagement.model.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class CartItem {

    private int quantity;

    @ManyToOne
    private Item item;
}
