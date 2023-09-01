package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            SELECT o
            FROM Order o
            WHERE o.user.id = :id
            ORDER BY o.created_at desc
            LIMIT 1
            """)
    Order findLastByUserId(@Param("id") long id);

    @Query("""
            SELECT new com.neshan.restaurantmanagement.model.dto.SalesStatsDto(COUNT(*), SUM(o.totalCost))
            FROM Order o
            WHERE o.created_at BETWEEN :from AND :to
            """)
    SalesStatsDto getSalesStats(
            @Param("from") Date from,
            @Param("to") Date to);

    @Query("""
            SELECT new com.neshan.restaurantmanagement.model.dto.SalesStatsDto(COUNT(*), SUM(o.totalCost))
            FROM Order o
            WHERE o.created_at BETWEEN :daysAgo AND :current
            """)
    SalesStatsDto getSalesStatsOfLastDays(
            @Param("daysAgo") Date daysAgo,
            @Param("current") Date current);

    @Query(value = """
            SELECT i.name, oi.quantity
            FROM orders o
            INNER JOIN order_items oi ON o.id = oi.order_id
            INNER JOIN items i on i.id = oi.item_id
            WHERE o.created_at BETWEEN :from AND :to
            ORDER BY oi.quantity DESC
            LIMIT 5
             """, nativeQuery = true)
    List<Object[]> getTopItems(
            @Param("from") Date from,
            @Param("to") Date to
    );

    @Query(value = """
            SELECT i.name, oi.quantity
            FROM orders o
            INNER JOIN order_items oi ON o.id = oi.order_id
            INNER JOIN items i on i.id = oi.item_id
            WHERE o.created_at BETWEEN :daysAgo AND :current
            ORDER BY oi.quantity DESC
            LIMIT 5
             """, nativeQuery = true)
    List<Object[]> getTopItemsOfLastDays(
            @Param("daysAgo") Date daysAgo,
            @Param("current") Date current);
}
