package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
