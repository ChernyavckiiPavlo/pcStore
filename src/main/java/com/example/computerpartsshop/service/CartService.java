package com.example.computerpartsshop.service;

import com.example.computerpartsshop.model.Cart;
import com.example.computerpartsshop.model.CartItem;
import com.example.computerpartsshop.model.Product;
import com.example.computerpartsshop.model.User;
import com.example.computerpartsshop.repository.CartItemRepository;
import com.example.computerpartsshop.repository.CartRepository;
import com.example.computerpartsshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItem> getCartItems(User user) {
        Cart cart = cartRepository.findByUser(user);
        return cart != null ? cart.getCartItems() : null;
    }

    public void addItemToCart(User user, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(new CartItem());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getQuantity() + quantity);

        cartItemRepository.save(cartItem);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
    }

    public void clearCart(User user) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            cartItemRepository.deleteAll(cart.getCartItems());
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    public void updateCartItem(User user, Long cartItemId, int quantity) {
        Cart cart = cartRepository.findByUser(user);
        if (cart != null) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid cart item ID"));
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid cart item ID"));
        cartItemRepository.delete(cartItem);
    }
}
