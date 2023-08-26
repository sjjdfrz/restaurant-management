package com.neshan.restaurantmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @SequenceGenerator(
            name = "menu_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "menu_menuitem",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id"))
    List<MenuItem> menuItems;

    @CreatedDate
    private Date created_at;

    @LastModifiedDate
    private Date modified_at;
}
