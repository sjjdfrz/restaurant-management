package com.neshan.restaurantmanagement.service;

import com.neshan.restaurantmanagement.exception.NoSuchElementFoundException;
import com.neshan.restaurantmanagement.mapper.CartMapper;
import com.neshan.restaurantmanagement.model.dto.CartDto;
import com.neshan.restaurantmanagement.model.dto.CartRequestDto;
import com.neshan.restaurantmanagement.model.entity.Cart;
import com.neshan.restaurantmanagement.model.entity.CartItem;
import com.neshan.restaurantmanagement.model.entity.Item;
import com.neshan.restaurantmanagement.model.entity.User;
import com.neshan.restaurantmanagement.repository.CartRepository;
import com.neshan.restaurantmanagement.repository.ItemRepository;
import com.neshan.restaurantmanagement.repository.UserRepository;
import com.neshan.restaurantmanagement.util.PaginationSorting;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {

    private CartMapper cartMapper;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    @Transactional
    public List<CartDto> getAllCarts(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> orders = PaginationSorting.getOrders(sortBy);
        Pageable paging = PaginationSorting.getPaging(pageNo, pageSize, orders);

        return cartRepository
                .findAll(paging)
                .map(cart -> cartMapper.cartToCartDto(cart))
                .getContent();
    }

    @Transactional
    public CartDto getCart(long id) {

        Cart cart = cartRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The cart with ID %d was not found.", id)));

        return cartMapper.cartToCartDto(cart);
    }

    @Transactional
    public List<CartDto> getAllCartsOfUser(HttpServletRequest request) {

        User user = (User) request.getAttribute("user");
        return user
                .getCarts()
                .stream()
                .map(cart -> cartMapper.cartToCartDto(cart))
                .toList();
    }

    @Transactional
    public CartDto getCartOfUser(HttpServletRequest request, long id) {

        User user = (User) request.getAttribute("user");

        Cart userCart = user
                .getCarts()
                .stream()
                .filter(cart -> cart.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The cart with ID %d was not found.", id)));

        return cartMapper.cartToCartDto(userCart);
    }

    @Transactional
    public void createCart(CartRequestDto cartRequestDto, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");
        Cart cart = new Cart();

        if (cartRequestDto.cartItems() == null || cartRequestDto.cartItems().isEmpty()) {
            user.addCart(cart);
            userRepository.save(user);
            return;
        }

        addCartItemToCart(cartRequestDto, cart);

        user.addCart(cart);
        userRepository.save(user);
    }

    @Transactional
    public void updateCart(long id, CartRequestDto cartRequestDto) {

        Cart cart = cartRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The cart with ID %d was not found.", id)));

        if (cartRequestDto.cartItems() == null || cartRequestDto.cartItems().isEmpty())
            return;

        addCartItemToCart(cartRequestDto, cart);

        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllCarts(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        user.getCarts().clear();
        userRepository.save(user);
    }

    private void addCartItemToCart(CartRequestDto cartRequestDto, Cart cart) {

        cartRequestDto
                .cartItems()
                .forEach(cartItem -> {

                    int quantity = cartItem.quantity();
                    long itemId = cartItem.itemId();

                    Item item = itemRepository
                            .findById(itemId)
                            .orElseThrow(() -> new NoSuchElementFoundException(
                                    String.format("The item with ID %d was not found.", itemId)));

                    CartItem newCartItem = new CartItem();
                    newCartItem.setItem(item);
                    newCartItem.setQuantity(quantity);

                    cart.addCartItem(newCartItem);
                });
    }
}
