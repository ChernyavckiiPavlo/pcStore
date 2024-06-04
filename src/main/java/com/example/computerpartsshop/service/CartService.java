package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Product;
import com.example.computerpartsshop.repository.CartItemRepository;
import com.example.computerpartsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository; // Добавление ProductRepository

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public void addProductToCart(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItemRepository.save(cartItem);
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }

    public void clearCart() {
        cartItemRepository.deleteAll();
    }
}
