package com.snacktrack.snacktrack.controller;

import com.snacktrack.snacktrack.model.User;
import com.snacktrack.snacktrack.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Show Signup Page
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Handle Signup
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists!");
            return "signup";
        }
        userRepository.save(user);
        model.addAttribute("message", "Signup successful! Please login.");
        return "redirect:/login";
    }

    // Show Login Page
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle Login
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("userId", existingUser.getId());
            session.setAttribute("userName", existingUser.getName());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    // Handle Logout
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
 // Show Home Page
    @GetMapping("/") 
    public String showHomePage(HttpSession session, Model model) {
        // Check if the user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "home"; // This will return home.html
    }
}
