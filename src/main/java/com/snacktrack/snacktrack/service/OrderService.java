package com.snacktrack.snacktrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snacktrack.snacktrack.repository.OrderRepository;
import com.snacktrack.snacktrack.model.Orders;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
    
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}