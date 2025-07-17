package com.tdila.ecommerce_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartResponse {
    private List<CartProductDetail> items;
    private BigDecimal total;
}
