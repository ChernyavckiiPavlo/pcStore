package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.Order;
import com.example.computerpartsshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
