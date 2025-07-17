package com.tdila.ecommerce_backend.service;

import com.tdila.ecommerce_backend.dto.CartItemRequest;
import com.tdila.ecommerce_backend.dto.CartProductDetail;
import com.tdila.ecommerce_backend.dto.CartResponse;
import com.tdila.ecommerce_backend.model.Cart;
import com.tdila.ecommerce_backend.model.CartItem;
import com.tdila.ecommerce_backend.model.Product;
import com.tdila.ecommerce_backend.model.User;
import com.tdila.ecommerce_backend.respository.CartItemRepository;
import com.tdila.ecommerce_backend.respository.CartRepository;
import com.tdila.ecommerce_backend.respository.ProductRepository;
import com.tdila.ecommerce_backend.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartResponse addToCart(String userEmail, CartItemRequest request){
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserEmail(userEmail).orElseGet(() -> {
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(CartItem.builder().product(product).cart(cart).build());

        item.setQuantity(item.getQuantity() + request.getQuantity());
        cartItemRepository.save(item);

        cart.getItems().removeIf(i -> i.getProduct().getId().equals(product.getId()));
        cart.getItems().add(item);
        cartRepository.save(cart);

        return getCart(userEmail);
    }

    public CartResponse getCart(String userEmail){
        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartProductDetail> items = cart.getItems().stream()
                .map(i -> CartProductDetail.builder()
                        .name(i.getProduct().getName())
                        .quantity(i.getQuantity())
                        .price(i.getProduct().getPrice())
                        .subtotal(i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                        .build())
                .toList();

        BigDecimal total = items.stream()
                .map(CartProductDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .items(items)
                .total(total)
                .build();
    }

    public void removeFromCart(String userEmail, Long productId){
        Cart cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
    }
}
