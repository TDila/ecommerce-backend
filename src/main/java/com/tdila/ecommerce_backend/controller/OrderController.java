package com.tdila.ecommerce_backend.controller;

import com.tdila.ecommerce_backend.dto.OrderResponse;
import com.tdila.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOder(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(orderService.placeOrder(user.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.ok(orderService.getOrders(user.getUsername()));
    }
}
