package com.neshan.restaurantmanagement.repository;

import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void getSalesStats() {

        LocalDate fromLocalDate = LocalDate.parse("2023-09-01");
        LocalDate toLocalDate = LocalDate.parse("2023-10-01");
        Date from = Date.from(fromLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date to = Date.from(toLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        //given
        Order order1 = Order
                .builder()
                .totalCost(10000)
                .build();

        Order order2 = Order
                .builder()
                .totalCost(20000)
                .build();

        Order order3 = Order
                .builder()
                .totalCost(30000)
                .build();

        orderRepository.saveAll(List.of(order1, order2, order3));

        //when
        SalesStatsDto salesStatsDto = orderRepository.getSalesStats(from, to);

        //then
        assertThat(salesStatsDto.getOrderCounts()).isEqualTo(3);
        assertThat(salesStatsDto.getTotalSales()).isEqualTo(60000);
    }
}