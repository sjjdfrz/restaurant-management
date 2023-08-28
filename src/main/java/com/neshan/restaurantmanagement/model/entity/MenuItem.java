package com.neshan.restaurantmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "menu_item")
@EntityListeners(AuditingEntityListener.class)
public class MenuItem {

    @Id
    @SequenceGenerator(
            name = "menu_item_sequence",
            sequenceName = "menu_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private long id;

    private String name;
    private double price;
    private String description;
    private String category;

    @CreatedDate
    @JsonIgnore
    private Date created_at;

    @LastModifiedDate
    @JsonIgnore
    private Date modified_at;
}
