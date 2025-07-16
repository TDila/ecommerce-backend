package com.tdila.ecommerce_backend.controller;

import com.tdila.ecommerce_backend.dto.ProductRequest;
import com.tdila.ecommerce_backend.dto.ProductResponse;
import com.tdila.ecommerce_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.addProduct(request));
    }
}
