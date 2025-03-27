package com.microservice.cart_service.service;

import com.microservice.cart_service.dto.CartDto;
import com.microservice.cart_service.entity.Cart;
import com.microservice.cart_service.entity.User;
import com.microservice.cart_service.repository.CartRepository;
import com.microservice.cart_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartDto createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        Cart savedCart = cartRepository.save(cart);
        return modelMapper.map(savedCart, CartDto.class);
    }

    @Override
    public CartDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new EntityNotFoundException("Cart not found");
        }
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}
