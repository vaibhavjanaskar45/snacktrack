package com.snacktrack.snacktrack.controller;

import com.snacktrack.snacktrack.model.CartItem;
import com.snacktrack.snacktrack.model.Product;
import com.snacktrack.snacktrack.model.User;
import com.snacktrack.snacktrack.repository.CartItemRepository;
import com.snacktrack.snacktrack.repository.ProductRepository;
import com.snacktrack.snacktrack.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Add Product to Cart
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login"; // Redirect to login if user is not logged in
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            CartItem cartItem = cartItemRepository.findByProductAndUserId(product, userId);
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(1); // Default quantity
                cartItem.setUserId(userId);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            }
            cartItemRepository.save(cartItem);
        }
        return "redirect:/cart";
    }

    // Show Cart Page
    @GetMapping("/cart")
    public String showCartPage(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        double totalPrice = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    // Update Quantity and Delete remain same as before

    // Update Quantity
    @PostMapping("/update-cart")
    public String updateCart(@RequestParam Long cartItemId, @RequestParam int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
        return "redirect:/cart";
    }

    // Delete Product from Cart
    @PostMapping("/delete-cart-item")
    public String deleteCartItem(@RequestParam Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return "redirect:/cart";
    }

}
