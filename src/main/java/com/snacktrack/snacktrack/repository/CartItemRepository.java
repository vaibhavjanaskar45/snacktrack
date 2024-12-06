package com.snacktrack.snacktrack.repository;

import com.snacktrack.snacktrack.model.CartItem;
import com.snacktrack.snacktrack.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByProductAndUserId(Product product, Long userId);
    List<CartItem> findByUserId(Long userId);
}
