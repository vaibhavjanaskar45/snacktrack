package com.snacktrack.snacktrack.controller;

import com.snacktrack.snacktrack.model.Product;
import com.snacktrack.snacktrack.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Show Products Page
    @GetMapping("/products")
    public String showProductsPage(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products";
    }
}
