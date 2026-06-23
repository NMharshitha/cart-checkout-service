package com.ecommerce.dto.request;

import lombok.Data;

@Data
public class ApplyCouponRequest {

    private Long userId;

    private String couponCode;
}