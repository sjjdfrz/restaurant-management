package com.neshan.restaurantmanagement.model.entity;

import com.neshan.restaurantmanagement.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    @Column(updatable = false)
    private long id;

    private double totalCost;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    String deliveryTime;

    @Builder.Default
    private boolean deleted = false;

    @ElementCollection
    private List<CartItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date modified_at;

    public void addCartItem(CartItem cartItem) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(cartItem);
    }
}
