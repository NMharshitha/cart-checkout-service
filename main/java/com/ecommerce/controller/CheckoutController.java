package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.service.CheckoutService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    @PostMapping("/{userId}")
    public Order checkout(

            @PathVariable Long userId,

            @RequestParam boolean paymentSuccess) {

        return checkoutService.checkout(

                userId,

                paymentSuccess);
    }

}