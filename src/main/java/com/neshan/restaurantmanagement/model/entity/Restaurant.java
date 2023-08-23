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

    @ManyToMany
    @JoinTable(
            name = "restaurant_menu",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus;

    @ManyToMany
    @JoinTable(
            name = "restaurant_order",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;


}
