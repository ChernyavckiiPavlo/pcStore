package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Product;
import com.example.computerpartsshop.repository.CartItemRepository;
import com.example.computerpartsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public void addProductToCart(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItemRepository.save(cartItem);
        logger.info("Added product to cart: " + productId);
    }

    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);
        logger.info("Removed cart item: " + id);
    }

    @Transactional
    public void clearCart() {
        cartItemRepository.deleteAll();
        logger.info("Cart cleared");
    }
}
