package com.snacktrack.snacktrack.repository;

import com.snacktrack.snacktrack.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId(Long userId);
    void deleteById(Long id);
}
