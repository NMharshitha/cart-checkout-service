package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.enums.OrderStatus;
import com.ecommerce.service.CheckoutService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;


    @Test
    void shouldReturnSuccessResponse_whenCheckoutIsValid()
            throws Exception {

        Order order = new Order();

        order.setStatus(OrderStatus.SUCCESS);

        order.setTotalAmount(
                BigDecimal.valueOf(1000));

        when(checkoutService.checkout(
                anyLong(),
                anyBoolean()))
                .thenReturn(order);

        mockMvc.perform(

                post("/api/checkout/1")
                        .param(
                                "paymentSuccess",
                                "true"
                        )
        )

        .andExpect(status().isOk())

        .andExpect(
                jsonPath("$.status")
                        .value("SUCCESS")
        );
    }

}