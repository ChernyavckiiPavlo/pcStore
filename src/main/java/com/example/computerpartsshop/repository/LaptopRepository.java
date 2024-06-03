package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}
