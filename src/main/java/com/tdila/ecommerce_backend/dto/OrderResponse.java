package com.tdila.ecommerce_backend.dto;

import com.tdila.ecommerce_backend.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private BigDecimal total;
    private LocalDateTime date;
    private OrderStatus status;
    private List<OrderItemDetail> items;
}
