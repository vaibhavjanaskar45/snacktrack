package com.snacktrack.snacktrack.repository;

import com.snacktrack.snacktrack.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // You can add custom queries here if needed
}
