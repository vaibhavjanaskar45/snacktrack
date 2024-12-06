package com.snacktrack.snacktrack.repository;

import com.snacktrack.snacktrack.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
