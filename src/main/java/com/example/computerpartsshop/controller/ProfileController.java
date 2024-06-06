package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.service.OrderService;
import com.example.computerpartsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public ProfileController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.getOrdersByUser(user));
        return "profile";
    }
}
