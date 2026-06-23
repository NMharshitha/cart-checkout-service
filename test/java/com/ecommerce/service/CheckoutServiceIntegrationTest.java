package com.ecommerce.service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.enums.OrderStatus;
import com.ecommerce.exception.PaymentFailedException;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutServiceIntegrationTest {


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
void shouldCompleteCheckoutSuccessfully() {

    User user = new User();
    user.setName("John");
    user.setEmail("john@test.com");

    user = userRepository.save(user);


    Product product = new Product();
    product.setName("Laptop");
    product.setPrice(BigDecimal.valueOf(50000));
    product.setStockQuantity(10);

    product = productRepository.save(product);


    Cart cart = new Cart();
    cart.setUser(user);
    cart.setItems(new ArrayList<>());


    CartItem item = new CartItem();
    item.setCart(cart);
    item.setProduct(product);
    item.setQuantity(2);

    cart.getItems().add(item);

    cartRepository.save(cart);


    Order order =
            checkoutService.checkout(
                    user.getId(),
                    true);


    assertNotNull(order);

    assertEquals(
            OrderStatus.SUCCESS,
            order.getStatus());

    assertNotNull(order.getId());

    assertTrue(
            orderRepository.findAll().size() > 0);
}



@Test
void shouldFailCheckout_whenPaymentFails() {
	User user = new User();
	user.setName("David");
	user.setEmail("david@test.com");

	User savedUser = userRepository.save(user);

	Product product = new Product();
	product.setName("Mouse");
	product.setPrice(BigDecimal.valueOf(1000));
	product.setStockQuantity(5);

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
	        PaymentFailedException.class,
	        () -> checkoutService.checkout(
	                savedUser.getId(),
	                false)
	);
}
}