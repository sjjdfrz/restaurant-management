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
    public List<CartDto> getAllCartsOfUser(User user) {

        return cartRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(cart -> cartMapper.cartToCartDto(cart))
                .toList();
    }

    @Transactional
    public CartDto getCartOfUser(User user, long id) {

        Cart userCart = cartRepository.findByUserIdAndId(user.getId(), id);
        return cartMapper.cartToCartDto(userCart);
    }

    @Transactional
    public void createCart(CartRequestDto cartRequestDto, User user) {

        Cart cart = new Cart();
        cart.setUser(user);

        if (cartRequestDto.getCartItems() == null || cartRequestDto.getCartItems().isEmpty()) {
            cartRepository.save(cart);
            return;
        }

        addCartItemToCart(cartRequestDto, cart);
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCart(long id, CartRequestDto cartRequestDto) {

        Cart cart = cartRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(
                        String.format("The cart with ID %d was not found.", id)));

        if (cartRequestDto.getCartItems() == null || cartRequestDto.getCartItems().isEmpty())
            return;

        addCartItemToCart(cartRequestDto, cart);
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllCarts(User user) {
        user.getCarts().clear();
        userRepository.save(user);
    }

    private void addCartItemToCart(CartRequestDto cartRequestDto, Cart cart) {

        cartRequestDto
                .getCartItems()
                .forEach(cartItem -> {

                    int quantity = cartItem.getQuantity();
                    long itemId = cartItem.getItemId();

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
