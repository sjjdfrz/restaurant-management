package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.OrderMapper;
import com.neshan.restaurantmanagement.model.enums.OrderStatus;
import com.neshan.restaurantmanagement.model.dto.ItemStatsDto;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.dto.OrdersDto;
import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Cart;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.CartRepository;
import com.neshan.restaurantmanagement.repository.OrderRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private CartRepository cartRepository;

    @Transactional
    public List<OrdersDto> getAllOrders(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return orderRepository
                .findAll(paging)
                .map(order -> orderMapper.orderToOrdersDto(order))
                .getContent();
    }

    @Transactional
    public OrderDto getOrder(long id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        return orderMapper.orderToOrderDto(order);
    }

    @Transactional
    public List<OrdersDto> getAllOrdersOfUser(User user) {

        return orderRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(order -> orderMapper.orderToOrdersDto(order))
                .toList();
    }

    @Transactional
    public OrderDto getOrderOfUser(User user, long id) {

        Order userOrder = orderRepository.findByUserIdAndId(user.getId(), id);
        return orderMapper.orderToOrderDto(userOrder);
    }

    @Transactional
    public OrdersDto createOrder(long cartId, User user) {

        Cart userCart = cartRepository
                .findById(cartId)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The cart with ID %d was not found.", cartId)));

        Integer totalCost = userCart
                .getCartItems()
                .stream()
                .map(item -> item.getQuantity() * item.getItem().getPrice())
                .reduce(0, Integer::sum);

        Order order = Order
                .builder()
                .totalCost(totalCost)
                .orderStatus(OrderStatus.PREPARING)
                .user(user)
                .deliveryTime((new Random().nextInt(31) + 30) + "دقیقه")
                .build();

        userCart
                .getCartItems()
                .forEach(order::addCartItem);

        orderRepository.save(order);
        return orderMapper.orderToOrdersDto(orderRepository.findLastByUserId(user.getId()));
    }

    @Transactional
    public void updateOrder(long id, OrdersDto orderRequest) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        orderMapper.updateOrderFromDto(orderRequest, order);
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    @Transactional
    public SalesStatsDto getSalesStats(Date from, Date to) {
        return orderRepository.getSalesStats(from, to);
    }

    @Transactional
    public SalesStatsDto getSalesStatsOfLastDays(int days) {

        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        cal.add(Calendar.DATE, -days);
        Date daysAgo = cal.getTime();

        return orderRepository.getSalesStatsOfLastDays(daysAgo, current);
    }

    @Transactional
    public List<ItemStatsDto> getTopItems(Date from, Date to) {

        return orderRepository.getTopItems(from, to);
    }

    @Transactional
    public List<ItemStatsDto> getTopItemsOfLastDays(int days) {

        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        cal.add(Calendar.DATE, -days);
        Date daysAgo = cal.getTime();

        return orderRepository.getTopItemsOfLastDays(daysAgo, current);
    }

    @Transactional
    public List<Order> getOrdersBetween(Date from, Date to) {
        return orderRepository.findByCreatedAtBetween(from, to);
    }
}
