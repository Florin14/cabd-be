package com.cabd.cabd.service;

import com.cabd.cabd.dao.dto.OrderRequestDTO;
import com.cabd.cabd.dao.model.Order;
import com.cabd.cabd.dao.model.Product;
import com.cabd.cabd.dao.repository.OrderRepository;
import com.cabd.cabd.dao.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order placeOrder(OrderRequestDTO orderDTO) {  // Assuming you're using a DTO for request payload
        Order order = new Order();
        order.setUsername(orderDTO.getUsername());
        order.setQuantity(orderDTO.getQuantity());

        // Fetch product from database using productId
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderDTO.getProductId()));

        // Set the full Product entity, not just the ID
        order.setProduct(product);

        return orderRepository.save(order);
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
