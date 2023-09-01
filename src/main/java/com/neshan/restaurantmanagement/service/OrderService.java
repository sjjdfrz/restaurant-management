package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.OrderMapper;
import com.neshan.restaurantmanagement.model.OrderStatus;
import com.neshan.restaurantmanagement.model.dto.ItemStatsDto;
import com.neshan.restaurantmanagement.model.dto.OrderDto;
import com.neshan.restaurantmanagement.model.dto.SalesStatsDto;
import com.neshan.restaurantmanagement.model.entity.Cart;
import com.neshan.restaurantmanagement.model.entity.Order;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.OrderRepository;
import com.neshan.restaurantmanagement.repository.UserRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private UserRepository userRepository;

    public List<OrderDto> getAllOrders(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return orderRepository
                .findAll(paging)
                .map(order -> orderMapper.orderToOrderDto(order))
                .getContent();
    }

    public OrderDto getOrder(long id) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        return orderMapper.orderToOrderDto(order);
    }

    public List<OrderDto> getAllOrdersOfUser(HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        return user
                .getOrders()
                .stream()
                .map(order -> orderMapper.orderToOrderDto(order))
                .toList();
    }

    public OrderDto getOrderOfUser(HttpServletRequest request, long id) {

        User user = (User) request.getAttribute("user");

        Order userOrder = user
                .getOrders()
                .stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        return orderMapper.orderToOrderDto(userOrder);
    }


    public OrderDto createOrder(long cartId, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");

        Cart userCart = user
                .getCarts()
                .stream()
                .filter(cart -> cart.getId() == cartId)
                .findFirst()
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
                .items(userCart.getCartItems())
                .build();

        user.addOrder(order);
        userRepository.save(user);
        return orderMapper.orderToOrderDto(orderRepository.findLastByUserId(user.getId()));
    }

    public void updateOrder(long id, OrderDto orderRequest) {

        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The order with ID %d was not found.", id)));

        orderMapper.updateOrderFromDto(orderRequest, order);
        orderRepository.save(order);
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public SalesStatsDto getSalesStats(Date from, Date to) {
        return orderRepository.getSalesStats(from, to);
    }

    public SalesStatsDto getSalesStatsOfLastDays(int days) {

        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        cal.add(Calendar.DATE, -days);
        Date daysAgo = cal.getTime();

        return orderRepository.getSalesStatsOfLastDays(daysAgo, current);
    }

    public List<ItemStatsDto> getTopItems(Date from, Date to) {

        List<Object[]> rawResult = orderRepository.getTopItems(from, to);
        return getItemStatsDtos(rawResult);
    }

    public List<ItemStatsDto> getTopItemsOfLastDays(int days) {

        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        cal.add(Calendar.DATE, -days);
        Date daysAgo = cal.getTime();

        List<Object[]> rawResult = orderRepository.getTopItemsOfLastDays(daysAgo, current);
        return getItemStatsDtos(rawResult);
    }

    private List<ItemStatsDto> getItemStatsDtos(List<Object[]> rawResult) {
        List<ItemStatsDto> topItems = new ArrayList<>();

        for (Object[] row : rawResult) {
            String itemName = (String) row[0];
            int quantity = (int) row[1];
            ItemStatsDto itemStatsDto = new ItemStatsDto(itemName, quantity);
            topItems.add(itemStatsDto);
        }
        return topItems;
    }
}
