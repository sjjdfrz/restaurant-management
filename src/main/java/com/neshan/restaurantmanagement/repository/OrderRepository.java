package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.dto.ItemStatsDto;
import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(long userId);

    Order findByUserIdAndId(long userId, long id);

    @Query("""
            SELECT o
            FROM Order o
            WHERE o.user.id = :id
            ORDER BY o.createdAt desc
            LIMIT 1
            """)
    Order findLastByUserId(@Param("id") long id);

    @Query(value = """
            SELECT COUNT(*) as orderCounts, SUM(o.total_cost) as totalSales
            FROM orders o
            WHERE o.created_at BETWEEN :from AND :to
            """, nativeQuery = true)
    SalesStatsDto getSalesStats(
            @Param("from") Date from,
            @Param("to") Date to);

    @Query(value = """
            SELECTCOUNT(*) as orderCounts, SUM(o.total_cost) as totalSales
            FROM orders o
            WHERE o.created_at BETWEEN :daysAgo AND :current
            """, nativeQuery = true)
    SalesStatsDto getSalesStatsOfLastDays(
            @Param("daysAgo") Date daysAgo,
            @Param("current") Date current);

    @Query(value = """
            SELECT i.name as name, oi.quantity as quantity
            FROM orders o
            INNER JOIN order_items oi ON o.id = oi.order_id
            INNER JOIN items i on i.id = oi.item_id
            WHERE o.created_at BETWEEN :from AND :to
            ORDER BY oi.quantity DESC
            LIMIT 5
             """, nativeQuery = true)
    List<ItemStatsDto> getTopItems(
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
    List<ItemStatsDto> getTopItemsOfLastDays(
            @Param("daysAgo") Date daysAgo,
            @Param("current") Date current);

    List<Order> findByCreatedAtBetween(
            @Param("from") Date from,
            @Param("to") Date to
    );
}
