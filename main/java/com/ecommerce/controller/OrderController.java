package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {

        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long id) {

        return orderRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(
            @PathVariable Long userId) {

        return orderRepository
                .findByUserId(
                        userId,
                        PageRequest.of(0, 10))
                .getContent();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestBody Order orderDetails) {

        return orderRepository.findById(id)
                .map(order -> {

                    order.setTotalAmount(
                            orderDetails.getTotalAmount());

                    order.setStatus(
                            orderDetails.getStatus());

                    return ResponseEntity.ok(
                            orderRepository.save(order));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(
            @PathVariable Long id) {

        orderRepository.deleteById(id);

        return "Order deleted successfully";
    }
}