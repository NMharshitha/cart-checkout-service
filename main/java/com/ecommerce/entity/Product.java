package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    private String name;

    private BigDecimal price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Version
    private Long version;
}