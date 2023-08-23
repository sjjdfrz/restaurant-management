package com.neshan.restaurantmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurant")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_cost")
    private double totalCost;

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<OrderItem> orderItems;
}
