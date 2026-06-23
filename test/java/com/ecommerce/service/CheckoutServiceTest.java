package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.enums.OrderStatus;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.impl.CheckoutServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PaymentService paymentService;


    @InjectMocks
    private CheckoutServiceImpl checkoutService;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldReturnSuccessOrder_whenPaymentIsSuccessful() {

        Order order =
                checkoutService.checkout(
                        1L,
                        true);

        assertNotNull(order);

        assertEquals(
                OrderStatus.SUCCESS,
                order.getStatus());
    }


    @Test
    void shouldThrowException_whenPaymentFails() {

        assertThrows(

                PaymentFailedException.class,

                () -> checkoutService.checkout(
                        1L,
                        false)
        );
    }
}