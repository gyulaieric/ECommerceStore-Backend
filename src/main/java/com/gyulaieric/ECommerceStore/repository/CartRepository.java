package com.gyulaieric.ECommerceStore.repository;

import com.gyulaieric.ECommerceStore.model.Cart;
import com.gyulaieric.ECommerceStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);
    List<Cart> findAllByUserId(Long userId);
    List<Cart> findAllByProductId(Long productId);
}