package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.model.OrderItem;
import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public Order createOrder(User user, String customerName, String customerAddress, String customerEmail, String customerPhone) {
        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);

        // Получаем товары из корзины пользователя
        List<CartItem> cartItems = cartService.getCartItems(user);

        // Преобразуем CartItem в OrderItem
        Set<OrderItem> orderItems = new HashSet<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(calculateTotalPrice(cartItems));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(user); // Очистка корзины пользователя
        return savedOrder;
    }

    public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return totalPrice;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
