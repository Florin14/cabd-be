package com.cabd.cabd.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Product_History")
@Getter
@Setter
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "History_ID")
    private Long historyId;

    @Column(name = "Product_ID")
    private Long productId;

    @Column(name = "Product_Name", nullable = false)
    private String name;

    @Column(name = "Price", nullable = false)
    private Double price;

    @Column(name = "Stock_Quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "Valid_From", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "Valid_To", nullable = false)
    private LocalDateTime validTo;

    @Column(name = "Change_Type", nullable = false)
    private LocalDateTime changeTime;

    @Column(name = "Hist_Date", nullable = false)
    private LocalDateTime histDate;
}
