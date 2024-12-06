package com.snacktrack.snacktrack.controller;

import com.snacktrack.snacktrack.model.CartItem;
import com.snacktrack.snacktrack.model.Orders;
import com.snacktrack.snacktrack.model.OrderItem;
import com.snacktrack.snacktrack.repository.CartItemRepository;
import com.snacktrack.snacktrack.repository.OrderRepository;
import com.snacktrack.snacktrack.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Show Checkout Page
    @GetMapping("/checkout")
    public String showCheckoutPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        double totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    // Handle Checkout
    @PostMapping("/checkout")
    public String handleCheckout(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Orders order = new Orders();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems); // Clear the cart after checkout

        return "redirect:/orders";
    }

    // Show Order History
    @GetMapping("/orders")
    public String showOrderHistory(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<Orders> orders = orderRepository.findByUserId(userId);
        model.addAttribute("orders", orders);
        return "order-history";
    }
}
