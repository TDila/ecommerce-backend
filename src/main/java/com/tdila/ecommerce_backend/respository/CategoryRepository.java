package com.tdila.ecommerce_backend.respository;

import com.tdila.ecommerce_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
