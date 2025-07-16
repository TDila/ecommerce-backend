package com.tdila.ecommerce_backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Integer quantity;
}
