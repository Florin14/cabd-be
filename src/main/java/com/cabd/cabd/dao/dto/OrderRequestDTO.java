package com.cabd.cabd.dao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private Long productId;
    private String username;
    private Integer quantity;

}