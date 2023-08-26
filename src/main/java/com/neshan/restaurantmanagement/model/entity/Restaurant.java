package com.neshan.restaurantmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private long telephone;
    private String address;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Menu> menus;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Order> orders;


}
