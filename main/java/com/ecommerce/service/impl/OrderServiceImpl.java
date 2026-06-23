package com.ecommerce.service.impl;

import com.ecommerce.dto.response.OrderItemResponse;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderResponse> getOrderHistory(
            Long userId,
            Pageable pageable) {

        Page<Order> orders =
                orderRepository.findByUserId(
                        userId,
                        pageable);

        return orders.map(this::convertToResponse);
    }

    private OrderResponse convertToResponse(
            Order order) {

        OrderResponse response =
                new OrderResponse();

        response.setOrderId(order.getId());

        response.setTotalAmount(
                order.getTotalAmount().doubleValue());

        response.setStatus(
                order.getStatus().name());

        response.setCreatedAt(
                order.getCreatedAt());

        List<OrderItemResponse> items =
                new ArrayList<>();

        for (OrderItem item : order.getItems()) {

            OrderItemResponse itemResponse =
                    new OrderItemResponse();

            itemResponse.setProductId(
                    item.getProduct().getId());

            itemResponse.setProductName(
                    item.getProduct().getName());

            itemResponse.setQuantity(
                    item.getQuantity());

            itemResponse.setPrice(
                    item.getPrice().doubleValue());

            items.add(itemResponse);
        }

        response.setItems(items);

        return response;
    }
}