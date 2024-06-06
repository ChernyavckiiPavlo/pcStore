package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.model.OrderItem;
import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.repository.OrderRepository;
import com.example.computerpartsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService,ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;

    }

    public Order createOrder(User user, String customerName, String customerAddress, String customerEmail, String customerPhone) {
        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(customerName);
        order.setCustomerAddress(customerAddress);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setCreatedAt(LocalDateTime.now());


        // Получаем товары из корзины пользователя
        List<CartItem> cartItems = cartService.getCartItems(user);

        // Преобразуем CartItem в OrderItem
        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
            totalPrice = totalPrice.add(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())));

        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(user); // Очистка корзины пользователя
        return savedOrder;
    }
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    public BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
