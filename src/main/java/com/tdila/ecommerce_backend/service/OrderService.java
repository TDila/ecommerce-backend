package com.tdila.ecommerce_backend.service;

import com.tdila.ecommerce_backend.dto.OrderItemDetail;
import com.tdila.ecommerce_backend.dto.OrderResponse;
import com.tdila.ecommerce_backend.model.*;
import com.tdila.ecommerce_backend.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderResponse placeOrder(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        if (cart.getItems().isEmpty()) throw new RuntimeException("Cart has no items");

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()){
            Product product = cartItem.getProduct();
            Integer qty = cartItem.getQuantity();

            Inventory inventory = inventoryRepository.findByProductId(product.getId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            if(inventory.getQuantity() < qty)
                throw new RuntimeException("Insufficient stock for product: "+product.getName());

            inventory.setQuantity(inventory.getQuantity() - qty);
            inventoryRepository.save(inventory);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(qty)
                    .price(product.getPrice())
                    .build();

            orderItems.add(orderItem);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(qty)));

        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PAID)
                .orderDate(LocalDateTime.now())
                .totalAmount(total)
                .items(orderItems)
                .build();

        order = orderRepository.save(order);
        for (OrderItem item : orderItems){
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        cart.getItems().clear();
        cartRepository.save(cart);

        return toResponse(order);

    }

    public List<OrderResponse> getOrders(String email){
        return orderRepository.findByUserEmail(email).stream()
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order){
        List<OrderItemDetail> items = order.getItems().stream()
                .map(i -> OrderItemDetail.builder()
                        .productName(i.getProduct().getName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .subtotal(i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .build())
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getStatus(),
                items
        );
    }
}
