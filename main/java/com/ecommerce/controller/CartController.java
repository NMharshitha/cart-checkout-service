package com.ecommerce.controller;

import com.ecommerce.dto.request.AddToCartRequest;
import com.ecommerce.dto.request.ApplyCouponRequest;
import com.ecommerce.dto.request.UpdateCartRequest;
import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(
            @RequestBody AddToCartRequest request) {

        cartService.addToCart(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Product added to cart"));
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity<ApiResponse> updateCart(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestBody UpdateCartRequest request) {

        cartService.updateCartItem(
                userId,
                productId,
                request);

        return ResponseEntity.ok(
                new ApiResponse("Cart updated"));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<ApiResponse> removeItem(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        cartService.removeFromCart(
                userId,
                productId);

        return ResponseEntity.ok(
                new ApiResponse("Item removed"));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                cartService.getCart(userId));
    }
    @PostMapping("/coupon")
    public ResponseEntity<ApiResponse>
    applyCoupon(
            @RequestBody
            ApplyCouponRequest request){

        cartService.applyCoupon(
                request.getUserId(),
                request.getCouponCode());

        return ResponseEntity.ok(

                new ApiResponse(
                        "Coupon Applied Successfully"));
    }
}