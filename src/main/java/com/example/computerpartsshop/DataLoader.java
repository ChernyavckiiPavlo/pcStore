package com.example.computerpartsshop;

import com.example.computerpartsshop.model.Laptop;
import com.example.computerpartsshop.model.Processor;
import com.example.computerpartsshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductService productService;

    @Autowired
    public DataLoader(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        Processor processor = new Processor();
        processor.setName("Процесор AMD Ryzen 5");
        processor.setPrice(3500.00);
        processor.setPhotoUrl("/images/ryzen5.jpg");
        processor.setBrand("AMD");
        processor.setCores(6);
        processor.setFrequency(3.6);
        processor.setSocketType("AM4");
        processor.setWarrantyMonths(36);
        processor.setCountryOfOrigin("USA");
        productService.saveProduct(processor);

        Laptop laptop = new Laptop();
        laptop.setName("Ноутбук Dell XPS 15");
        laptop.setPrice(15000.00);
        laptop.setPhotoUrl("/images/dellxps15.jpg");
        laptop.setScreenDiagonal(15.6);
        laptop.setScreenType("IPS");
        laptop.setRefreshRate(60);
        laptop.setResolution("1920x1080");
        laptop.setProcessorName("Intel Core i7");
        laptop.setRam(16);
        laptop.setSsdCapacity(512);
        laptop.setGpu("NVIDIA GTX 1650");
        productService.saveProduct(laptop);
    }
}
