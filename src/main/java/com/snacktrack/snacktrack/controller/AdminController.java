package com.snacktrack.snacktrack.controller;

import com.snacktrack.snacktrack.model.Product;
import com.snacktrack.snacktrack.repository.FeedbackRepository;
import com.snacktrack.snacktrack.repository.ProductRepository;
import com.snacktrack.snacktrack.repository.UserRepository;
import com.snacktrack.snacktrack.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.snacktrack.snacktrack.repository.OrderRepository;
import com.snacktrack.snacktrack.model.Feedback;
import com.snacktrack.snacktrack.model.Orders;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    
    private final OrderRepository ordersRepository;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private FeedbackRepository feedbackRepository;
    
    public AdminController(OrderRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    // Admin Dashboard - Show number of users and products
    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        long userCount = userRepository.count();
        long productCount = productRepository.count();

        model.addAttribute("userCount", userCount);
        model.addAttribute("productCount", productCount);

        return "admin/dashboard";
    }

    // Manage Users - List all users
    @GetMapping("/admin/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/manageUsers"; // A page to list all users
    }

    // Delete User
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    // Manage Products - List all products
    @GetMapping("/admin/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/manageProducts"; // A page to list all products
    }

    // Delete Product
    @GetMapping("/admin/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    // Show Add Product Form
    @GetMapping("/admin/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/addProduct";
    }

    // Handle Add Product
    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // Show Edit Product Form
    @GetMapping("/admin/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "admin/editProduct";
    }

    // Handle Update Product
    @PostMapping("/admin/products/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productRepository.save(product);
        return "redirect:/admin/products";
    }
    
    
    
    @GetMapping("/admin/orders")
    public String showAdminOrders(Model model) {
        List<Orders> orders = ordersRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin/admin-order-management";
    }
    
    @PostMapping("/admin/orders/update/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String orderStatus) {
        Orders order = ordersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + id));
        order.setOrderStatus(orderStatus);
        ordersRepository.save(order);
        return "redirect:/admin/orders";  // Redirect back to the admin page
    }
    
    @PostMapping("admin/orders/{id}/delete")
    public String deleteOrder(@PathVariable("id") Long orderId) {
        orderService.deleteOrderById(orderId);
        return "redirect:/admin/orders"; // Redirect back to the order list page
    }

    @GetMapping("/admin/feedbacks")
    public String showFeedbacks(Model model) {
        List<Feedback> feedbackList = feedbackRepository.findAll();
        model.addAttribute("feedbacks", feedbackList);
        return "admin/manageFeedbacks";
    }
 // Delete feedback
    @GetMapping("/admin/feedbacks/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackRepository.deleteById(id);
        return "redirect:/admin/feedbacks";
    }
}
