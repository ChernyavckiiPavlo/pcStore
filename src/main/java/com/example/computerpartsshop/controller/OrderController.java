package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.service.OrderService;
import com.example.computerpartsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        Order order = new Order();
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            order.setUser(currentUser);
        }
        model.addAttribute("order", order);
        model.addAttribute("user", currentUser); // Добавлено для передачи user в шаблон
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute Order order) {
        orderService.createOrder(order.getCustomerName(), order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone(), order.getUser().getId());
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation() {
        return "confirmation";
    }
}
