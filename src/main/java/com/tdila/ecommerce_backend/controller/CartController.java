package com.tdila.ecommerce_backend.controller;

import com.tdila.ecommerce_backend.dto.CartItemRequest;
import com.tdila.ecommerce_backend.dto.CartResponse;
import com.tdila.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestBody CartItemRequest request,
                                                   @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(cartService.addToCart(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<CartResponse> viewCart(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(cartService.getCart(userDetails.getUsername()));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId,
                                               @AuthenticationPrincipal UserDetails userDetails){
        cartService.removeFromCart(userDetails.getUsername(), productId);
        return ResponseEntity.ok().build();
    }
}
