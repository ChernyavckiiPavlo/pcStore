package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.Cart;
import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
