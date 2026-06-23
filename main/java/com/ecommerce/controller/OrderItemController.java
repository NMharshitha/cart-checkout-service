package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @PostMapping
    public OrderItem create(@RequestBody OrderItem orderItem) {

        Order order = orderRepository
                .findById(orderItem.getOrder().getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository
                .findById(orderItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orderItem.setOrder(order);
        orderItem.setProduct(product);

        return orderItemRepository.save(orderItem);
    }
//    @PostMapping
//    public OrderItem create(@RequestBody OrderItem orderItem) {
//
//        System.out.println("Order = " + orderItem.getOrder());
//        System.out.println("Product = " + orderItem.getProduct());
//        System.out.println("Quantity = " + orderItem.getQuantity());
//
//        return orderItem;
//    }
    
    
   
    @GetMapping
    public List<OrderItem> getAll() {
        return orderItemRepository.findAll();
    }

    @GetMapping("/{id}")
    public OrderItem getById(@PathVariable Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemRepository.deleteById(id);
    }
}