package com.neshan.restaurantmanagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private long id;

    private double totalCost;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @JsonIgnore
    private Date created_at;

    @LastModifiedDate
    @JsonIgnore
    private Date modified_at;
}
