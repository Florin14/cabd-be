package com.cabd.cabd.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_ID")
    private Long productId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Column(name = "Stock_Quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "Start_Date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "End_Date", nullable = false)
    private LocalDateTime EndDate;
}
