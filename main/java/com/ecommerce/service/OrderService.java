package com.ecommerce.service;

import com.ecommerce.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<OrderResponse> getOrderHistory(
            Long userId,
            Pageable pageable);
}