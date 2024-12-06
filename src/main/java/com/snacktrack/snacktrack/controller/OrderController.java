package com.snacktrack.snacktrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.snacktrack.snacktrack.model.Orders;
import com.snacktrack.snacktrack.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import com.snacktrack.snacktrack.repository.OrderRepository;
@Controller
public class OrderController {
	
    private final OrderRepository ordersRepository;

    public OrderController(OrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
	

    @Autowired
    private OrderService orderService;  

    @GetMapping("/orders/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        // Fetch the order details from the service
        Orders order = orderService.getOrderById(id);

        // Add the order details to the model
        model.addAttribute("order", order);

        // Return the view name (order-details.html)
        return "order-details";
    }
    


}
