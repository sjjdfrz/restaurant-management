package com.neshan.restaurantmanagement.model.entity;

import com.neshan.restaurantmanagement.model.ItemStatus;
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
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    @Column(updatable = false)
    private long id;

    private String name;

    private int price;

    private String description;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private ItemStatus itemStatus = ItemStatus.AVAILABLE;

    @Builder.Default
    private boolean deleted = false;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    List<Comment> comments;

    @CreatedDate
    private Date created_at;

    @LastModifiedDate
    private Date modified_at;

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
}
