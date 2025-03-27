package com.microservice.cart_service.repository;

import com.microservice.cart_service.entity.Cart;
import com.microservice.cart_service.entity.CartItem;
import com.microservice.cart_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Page<CartItem> findByCartId(Long cartId, Pageable pageable); // Tìm mục giỏ hàng theo ID giỏ hàng
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    @Query("SELECT ci FROM CartItem ci WHERE ci.id IN :ids AND ci.cart.id = :cartId")
    List<CartItem> findByIdInAndCartId(@Param("ids") List<Long> ids, @Param("cartId") Long cartId);
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.id IN :cartItemIds AND ci.cart.id = :cartId")
    int deleteByIdInAndCartId(
            @Param("cartItemIds") List<Long> cartItemIds,
            @Param("cartId") Long cartId
    );
}
