package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.Cart;
import com.example.computerpartsshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
