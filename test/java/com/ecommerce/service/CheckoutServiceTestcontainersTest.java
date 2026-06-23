package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.enums.OrderStatus;

import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.testcontainers.containers.MySQLContainer;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
class CheckoutServiceTestcontainersTest {

    @Container
    static MySQLContainer<?> mysql =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("ecommerce_test")
                    .withUsername("root")
                    .withPassword("root");


    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void shouldCheckoutSuccessfully() {

        User user = User.builder()
                .name("John")
                .email("john@test.com")
                .build();

        user = userRepository.save(user);


        Product product = Product.builder()
                .name("Laptop")
                .price(BigDecimal.valueOf(50000))
                .stockQuantity(10)
                .build();

        product = productRepository.save(product);


        Cart cart = new Cart();

        cart.setUser(user);

        cart.setItems(new ArrayList<>());


        CartItem item = new CartItem();

        item.setCart(cart);

        item.setProduct(product);

        item.setQuantity(1);


        cart.getItems().add(item);


        cartRepository.save(cart);


        Order order = checkoutService.checkout(
                user.getId(),
                true);


        assertNotNull(order);

        assertNotNull(order.getId());

        assertEquals(
                OrderStatus.SUCCESS,
                order.getStatus());

        assertTrue(
                orderRepository.findAll().size() > 0);
    }

    @Test
    void shouldFailCheckoutWhenPaymentFails() {

        User user = User.builder()
                .name("David")
                .email("david@test.com")
                .build();

        User savedUser = userRepository.save(user);


        Product product = Product.builder()
                .name("Mouse")
                .price(BigDecimal.valueOf(1000))
                .stockQuantity(5)
                .build();

        product = productRepository.save(product);


        Cart cart = new Cart();

        cart.setUser(savedUser);

        cart.setItems(new ArrayList<>());


        CartItem item = new CartItem();

        item.setCart(cart);

        item.setProduct(product);

        item.setQuantity(1);

        cart.getItems().add(item);

        cartRepository.save(cart);


        assertThrows(
                RuntimeException.class,
                () -> checkoutService.checkout(
                        savedUser.getId(),
                        false)
        );
    }
}