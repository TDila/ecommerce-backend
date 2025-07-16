package com.tdila.ecommerce_backend.service;

import com.tdila.ecommerce_backend.dto.ProductRequest;
import com.tdila.ecommerce_backend.dto.ProductResponse;
import com.tdila.ecommerce_backend.model.Category;
import com.tdila.ecommerce_backend.model.Inventory;
import com.tdila.ecommerce_backend.model.Product;
import com.tdila.ecommerce_backend.respository.CategoryRepository;
import com.tdila.ecommerce_backend.respository.InventoryRepository;
import com.tdila.ecommerce_backend.respository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;

    public ProductResponse addProduct(ProductRequest request){
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(category)
                .build();

        product = productRepository.save(product);

        Inventory inventory = Inventory.builder()
                .product(product)
                .quantity(request.getQuantity())
                .build();

        inventoryRepository.save(inventory);

        return toResponse(product, request.getQuantity());
    }

    private ProductResponse toResponse(Product product, Integer quantity){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory().getName())
                .quantity(quantity)
                .build();
    }
}
