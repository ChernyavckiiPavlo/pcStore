package com.example.computerpartsshop.controller;

import com.example.computerpartsshop.model.Product;
import com.example.computerpartsshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "catalog";
    }

    @GetMapping("/products/details/{id}")
    public String showProductDetails(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }
}
