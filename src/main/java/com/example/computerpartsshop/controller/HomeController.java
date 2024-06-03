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

    @GetMapping("/products/details/{id}")
    public String viewProductDetails(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<Product> products = productService.getFirstSevenProducts(); // Fetching only top 7 products
        model.addAttribute("products", products);
        return "home";
    }

    @GetMapping("/catalog")
    public String showCatalogPage(Model model) {
        List<Product> products = productService.getAllProducts(); // Fetching all products
        model.addAttribute("products", products);
        return "catalog";
    }
}
