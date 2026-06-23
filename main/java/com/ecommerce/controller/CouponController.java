package com.ecommerce.controller;

import com.ecommerce.dto.response.ApiResponse;
import com.ecommerce.entity.Coupon;
import com.ecommerce.repository.CouponRepository;
import com.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponRepository couponRepository;

    private final CartService cartService;


    @PostMapping
    public Coupon createCoupon(
            @RequestBody Coupon coupon) {

        return couponRepository.save(coupon);
    }


    @GetMapping
    public List<Coupon> getAllCoupons() {

        return couponRepository.findAll();
    }


    @GetMapping("/{code}")
    public Coupon getCoupon(
            @PathVariable String code) {

        return couponRepository
                .findByCode(code)
                .orElseThrow();
    }


    @DeleteMapping("/{code}")
    public void deleteCoupon(
            @PathVariable String code) {

        Coupon coupon = couponRepository
                .findByCode(code)
                .orElseThrow();

        couponRepository.delete(coupon);
    }


    @PostMapping("/apply/{userId}")
    public ResponseEntity<ApiResponse> applyCoupon(

            @PathVariable Long userId,

            @RequestParam String couponCode) {

        cartService.applyCoupon(
                userId,
                couponCode);

        return ResponseEntity.ok(

                new ApiResponse(
                        "Coupon applied successfully"));
    }
}