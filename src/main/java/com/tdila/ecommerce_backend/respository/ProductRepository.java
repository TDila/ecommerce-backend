package com.tdila.ecommerce_backend.respository;

import com.tdila.ecommerce_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
