package com.ecommerce.service;

import com.ecommerce.entity.Order;

public interface CheckoutService {

    Order checkout(Long userId, boolean paymentSuccess);

}