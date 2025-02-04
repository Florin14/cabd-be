package com.cabd.cabd.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_ID")
    private Long orderId;


    @OneToOne
    @JoinColumn(name = "Product_ID", referencedColumnName = "Product_ID", nullable = false)
    private Product product;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}
