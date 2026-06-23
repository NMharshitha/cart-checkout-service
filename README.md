# Cart Checkout Service

## Project Overview

Cart Checkout Service is a Spring Boot based E-Commerce backend application that manages:

* User Management
* Product Management
* Cart Management
* Coupon Management
* Checkout Process
* Payment Simulation
* Order Management

## Technologies Used

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* JUnit 5
* Testcontainers
* Postman

## Features

### User Module

* Create User
* Get User Details

### Product Module

* Add Product
* Update Product
* Delete Product
* View Products

### Cart Module

* Add Product to Cart
* Update Product Quantity
* Remove Product from Cart
* View Cart with Total Price

### Coupon Module

* Create Coupon
* Apply Percentage Discount
* Apply Flat Discount

### Checkout Module

* Validate Product Stock
* Create Order from Cart
* Simulate Payment (SUCCESS / FAILED)
* Reduce Inventory on Successful Payment
* Restore Inventory on Failed Payment
* Generate Order Summary

### Order Module

* View All Orders
* Get Order by ID
* Fetch Order History by User ID

## Database

Tables Used:

* users
* products
* carts
* cart_items
* orders
* order_items
* coupons

Database schema is available in:

src/main/resources/schema.sql

## Running the Application

1. Clone the repository

2. Configure MySQL in:

src/main/resources/application.properties

3. Run:

mvn spring-boot:run

4. Application runs on:

http://localhost:8080

## Running Tests

mvn test

## API Testing

Postman Collection:

cart-checkout-service.postman_collection.json

## Project Screenshots

Screenshots are available inside:

Project_Screenshots/

* User_API.png
* Product_API.png
* Cart_API.png
* Coupon_API.png
* Checkout_API.png
* OrderHistory_API.png
## Sample API Endpoints

### User APIs

* POST /api/users
* GET /api/users
* GET /api/users/{id}

### Product APIs

* POST /api/products
* GET /api/products

### Cart APIs

* POST /api/cart/add
* GET /api/cart/{userId}

### Coupon APIs

* POST /api/coupons
* GET /api/coupons

### Checkout APIs

* POST /api/checkout/{userId}?paymentSuccess=true

### Order APIs

* GET /api/orders
* GET /api/orders?page=0&size=5

## Author

Harshitha NM

Project: Cart Checkout Service using Spring Boot
