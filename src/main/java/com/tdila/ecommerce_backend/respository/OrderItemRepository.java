package com.tdila.ecommerce_backend.respository;

import com.tdila.ecommerce_backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
