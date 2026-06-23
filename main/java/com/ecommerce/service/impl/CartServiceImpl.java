package com.ecommerce.service.impl;

import com.ecommerce.dto.request.AddToCartRequest;
import com.ecommerce.dto.request.UpdateCartRequest;
import com.ecommerce.dto.response.CartItemResponse;
import com.ecommerce.dto.response.CartResponse;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Coupon;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.enums.DiscountType;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CouponRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CouponRepository couponRepository;


    @Override
    public void addToCart(AddToCartRequest request) {

        User user = userRepository.findById(
                request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Product product = productRepository.findById(
                request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        Cart cart = cartRepository.findByUserId(
                user.getId())
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem =
                cart.getItems()
                        .stream()
                        .filter(item ->
                                item.getProduct()
                                        .getId()
                                        .equals(product.getId()))
                        .findFirst();

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            item.setQuantity(
                    item.getQuantity()
                            + request.getQuantity());

            cartItemRepository.save(item);

        } else {

            CartItem item = new CartItem();

            item.setCart(cart);

            item.setProduct(product);

            item.setQuantity(
                    request.getQuantity());

            cart.getItems().add(item);

            cartItemRepository.save(item);
        }
    }


    @Override
    public void updateCartItem(
            Long userId,
            Long productId,
            UpdateCartRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        CartItem item = cart.getItems()
                .stream()
                .filter(i ->
                        i.getProduct()
                                .getId()
                                .equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"));

        item.setQuantity(
                request.getQuantity());

        cartItemRepository.save(item);
    }


    @Override
    public void removeFromCart(
            Long userId,
            Long productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        CartItem item = cart.getItems()
                .stream()
                .filter(i ->
                        i.getProduct()
                                .getId()
                                .equals(productId))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found"));

        cart.getItems().remove(item);

        cartItemRepository.delete(item);
    }


    @Override
    public void applyCoupon(
            Long userId,
            String couponCode) {

        Cart cart = cartRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        Coupon coupon = couponRepository
                .findByCode(couponCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Coupon not found"));

        cart.setCoupon(coupon);

        cartRepository.save(cart);
    }


    @Override
    public CartResponse getCart(Long userId) {

        Cart cart = cartRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"));

        CartResponse response =
                new CartResponse();

        response.setUserId(userId);

        ArrayList<CartItemResponse> items =
                new ArrayList<>();

        double total = 0;

        for (CartItem item : cart.getItems()) {

            double subTotal =

                    item.getProduct()
                            .getPrice()
                            .doubleValue()

                            * item.getQuantity();

            total += subTotal;

            items.add(

                    new CartItemResponse(

                            item.getProduct().getId(),

                            item.getProduct().getName(),

                            item.getQuantity(),

                            item.getProduct()
                                    .getPrice()
                                    .doubleValue(),

                            subTotal
                    )
            );
        }

        double discount = 0;

        if (cart.getCoupon() != null) {

            Coupon coupon = cart.getCoupon();

            if (coupon.getDiscountType()
                    == DiscountType.PERCENTAGE) {

                discount =

                        total *

                        coupon.getDiscountValue()
                                .doubleValue()

                        / 100;

            } else {

                discount =

                        coupon.getDiscountValue()
                                .doubleValue();
            }
        }

        double finalAmount = total - discount;

        response.setItems(items);

        response.setTotalPrice(total);

        response.setDiscount(discount);

        response.setFinalAmount(finalAmount);

        return response;
    }

}