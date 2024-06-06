package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Это будет искать шаблон login.html
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Это будет искать шаблон register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("users.username")) {
                model.addAttribute("usernameError", "Username is already taken.");
            } else if (e.getMessage().contains("users.email")) {
                model.addAttribute("emailError", "Email is already registered.");
            }
            return "register";
        }

        return "redirect:/login";
    }
}
