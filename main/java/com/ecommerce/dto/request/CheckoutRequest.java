package com.ecommerce.dto.request;

import lombok.Data;

@Data
public class CheckoutRequest {

    private Long userId;

    private String couponCode;
}