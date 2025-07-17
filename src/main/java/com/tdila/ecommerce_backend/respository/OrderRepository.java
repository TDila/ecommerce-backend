package com.tdila.ecommerce_backend.respository;

import com.tdila.ecommerce_backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserEmail(String email);
}
