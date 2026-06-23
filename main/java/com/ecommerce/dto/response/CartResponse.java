package com.ecommerce.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {

    private Long userId;

    private List<CartItemResponse> items;

    private Double totalPrice;

    private Double discount;

    private Double finalAmount;
}