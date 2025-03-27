package com.microservice.cart_service.service;

import com.microservice.cart_service.dto.CartItemDto;
import com.microservice.cart_service.entity.Cart;
import com.microservice.cart_service.entity.CartItem;
import com.microservice.cart_service.entity.Product;
import com.microservice.cart_service.form.CartItemCreateForm;
import com.microservice.cart_service.form.CartItemUpdateForm;
import com.microservice.cart_service.repository.CartItemRepository;
import com.microservice.cart_service.repository.CartRepository;
import com.microservice.cart_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartItemDto addCartItem(CartItemCreateForm form) {
        Cart cart = cartRepository.findById(form.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        Product product = productRepository.findById(form.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItem.isPresent()) {
            // Cập nhật số lượng nếu đã tồn tại
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + form.getQuantity());
            cartItemRepository.save(item);
            return modelMapper.map(item, CartItemDto.class);
        } else {
            // Thêm mới nếu chưa tồn tại
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(form.getQuantity());
            cartItemRepository.save(newItem);
            return modelMapper.map(newItem, CartItemDto.class);
        }
    }

    @Override
    public void updateCartItem(CartItemUpdateForm form) {
        CartItem cartItem = cartItemRepository.findById(form.getCartItemId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mục giỏ hàng"));
        if (!cartItem.getCart().getId().equals(form.getCartId())) {
            throw new EntityNotFoundException("Mục giỏ hàng không thuộc về giỏ hàng này");
        }
        cartItem.setQuantity(form.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(Long cartId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy mục giỏ hàng"));
        if (!cartItem.getCart().getId().equals(cartId)) {
            throw new EntityNotFoundException("Mục giỏ hàng không thuộc về giỏ hàng này");
        }
        cartItemRepository.delete(cartItem);
    }

    @Override
    public Page<CartItemDto> getCartItems(Long cartId, Pageable pageable) {
        return cartItemRepository.findByCartId(cartId, pageable)
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class));
    }

    @Override
    public void deleteCartItems(Long cartId, List<Long> cartItemIds) {
        // Xóa các item có ID trong danh sách và thuộc giỏ hàng cartId
        int deletedCount = cartItemRepository.deleteByIdInAndCartId(cartItemIds, cartId);
        if (deletedCount != cartItemIds.size()) {
            throw new EntityNotFoundException("Một số mục không tồn tại trong giỏ hàng");
        }
    }
}
