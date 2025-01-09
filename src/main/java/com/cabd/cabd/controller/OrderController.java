package com.cabd.cabd.controller;

import com.cabd.cabd.dao.model.Order;
import com.cabd.cabd.dao.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/client")
    public Order placeOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping("/admin")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
