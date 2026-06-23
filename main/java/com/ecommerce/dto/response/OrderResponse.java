package com.ecommerce.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;

    private Double totalAmount;

    private String status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}