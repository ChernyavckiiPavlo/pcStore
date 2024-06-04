package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.repository.CartItemRepository;
import com.example.computerpartsshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

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
        logger.info("Order created with id: " + order.getId());

        clearCart();

        return order;
    }

    private void clearCart() {
        cartItemRepository.deleteAll();
        logger.info("Cart cleared");
    }
}
