package com.neshan.restaurantmanagement.model.entity;

import com.neshan.restaurantmanagement.model.ItemStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
@Table(name = "items")
@SQLDelete(sql = "UPDATE items SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private long id;

    private String name;

    @Setter(AccessLevel.NONE)
    private int price;

    private String description;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private ItemStatus itemStatus = ItemStatus.AVAILABLE;

    private int discount;

    @Builder.Default
    private boolean deleted = false;

    @CreatedDate
    private Date created_at;

    @LastModifiedDate
    private Date modified_at;

    public void setPrice(int price) {

        if (discount != 0)
            this.price = price * ((100 - discount) / 100);
        else
            this.price = price;
    }
}
