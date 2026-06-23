package com.ecommerce.service.impl;

import com.ecommerce.entity.Order;
import com.ecommerce.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl
        implements PaymentService {

    @Override
    public boolean processPayment(Order order) {

        return Math.random() > 0.3;
    }
}