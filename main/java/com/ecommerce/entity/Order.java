package com.ecommerce.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ecommerce.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<OrderItem> items = new ArrayList<>();
}