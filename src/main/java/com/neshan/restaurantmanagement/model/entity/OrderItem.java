package com.neshan.restaurantmanagement.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {

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
    private int quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @CreatedDate
    private Date created_at;

    @LastModifiedDate
    private Date modified_at;
}
