package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        model.addAttribute("order", new Order());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute Order order) {
        orderService.createOrder(order.getCustomerName(), order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation() {
        return "confirmation";
    }
}
