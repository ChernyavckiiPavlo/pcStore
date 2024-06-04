package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
