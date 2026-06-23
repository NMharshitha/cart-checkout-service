package com.ecommerce.service;

import com.ecommerce.dto.request.AddToCartRequest;
import com.ecommerce.dto.request.UpdateCartRequest;
import com.ecommerce.dto.response.CartResponse;

public interface CartService {

    void addToCart(AddToCartRequest request);

    void updateCartItem(
            Long userId,
            Long productId,
            UpdateCartRequest request);

    void removeFromCart(
            Long userId,
            Long productId);

    void applyCoupon(
            Long userId,
            String couponCode);

    CartResponse getCart(Long userId);
}