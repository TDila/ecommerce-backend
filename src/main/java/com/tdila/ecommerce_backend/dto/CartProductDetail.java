package com.tdila.ecommerce_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartProductDetail {
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
