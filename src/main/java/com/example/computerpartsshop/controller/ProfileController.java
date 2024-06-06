package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.service.OrderService;
import com.example.computerpartsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewProfile(Model model) {
        User currentUser = userService.getCurrentUser();
        List<Order> userOrders = orderService.getOrdersByUser(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("orders", userOrders);
        return "profile";
    }
}
