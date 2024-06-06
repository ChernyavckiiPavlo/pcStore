package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.service.CartService;
import com.example.computerpartsshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity) {
        User user = userService.getCurrentUser();
        cartService.addItemToCart(user, productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model) {
        User user = userService.getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItems(user);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        User user = userService.getCurrentUser();
        cartService.clearCart(user);
        return "redirect:/cart";
    }
}
