package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.model.OrderItem;
import com.example.computerpartsshop.repository.CartItemRepository;
import com.example.computerpartsshop.repository.OrderItemRepository;
import com.example.computerpartsshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Transactional
    public Order createOrder(String customerName, String customerAddress, String customerEmail, String customerPhone, Long userId) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setUserId(userId);

        List<CartItem> cartItems = cartItemRepository.findAll();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemRepository.save(orderItem);
        }

        orderRepository.save(order);
        logger.info("Order created with id: " + order.getId() + " for user id: " + userId);

        cartService.clearCart();
        logger.info("Cart cleared after order creation");

        return order;
    }
}
