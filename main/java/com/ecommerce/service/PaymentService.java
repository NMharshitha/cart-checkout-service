package com.ecommerce.service;

import com.ecommerce.entity.Order;

public interface PaymentService {

    boolean processPayment(Order order);
}