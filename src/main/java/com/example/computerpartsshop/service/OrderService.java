package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.repository.CartItemRepository;
import com.example.computerpartsshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Order createOrder(String customerName, String customerAddress, String customerEmail, String customerPhone) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);

        List<CartItem> cartItems = cartItemRepository.findAll();
        for (CartItem cartItem : cartItems) {
            cartItem.setOrder(order);
        }
        order.setCartItems(cartItems);

        orderRepository.save(order);
        cartItemRepository.deleteAll();

        return order;
    }
}
