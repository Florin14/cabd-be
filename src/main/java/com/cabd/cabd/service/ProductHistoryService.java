package com.cabd.cabd.service;

import com.cabd.cabd.dao.model.Product;
import com.cabd.cabd.dao.model.ProductHistory;
import com.cabd.cabd.dao.repository.ProductHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    public List<Product> findByProductId(Long productId) {
        return productHistoryRepository.findByProductId(productId);
    }

    public List<Map<String, Object>> getPriceDifferences(Long productId) {
        // Fetch the history for the given productId and filter relevant records
        List<ProductHistory> histories = productHistoryRepository.findAll().stream()
                .filter(h -> h.getProduct().getProductId().equals(productId))
                .sorted(Comparator.comparing(ProductHistory::getValidFrom))
                .collect(Collectors.toList());

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < histories.size(); i++) {
            ProductHistory current = histories.get(i);
            Map<String, Object> entry = new HashMap<>();

            if (i == 0) {
                // Handle the first entry (initial price)
                entry.put("timestamp", current.getValidFrom().toString());
                entry.put("action", "Initial price");
                entry.put("price", current.getPrice());
            } else {
                // Handle price changes
                ProductHistory previous = histories.get(i - 1);

                entry.put("from", previous.getValidFrom().toString());
                entry.put("to", current.getValidFrom().toString());
                entry.put("oldPrice", previous.getPrice());
                entry.put("newPrice", current.getPrice());
            }

            result.add(entry);
        }

        return result;
    }


    public List<ProductHistory> getAllProductsHistories() {
        return productHistoryRepository.findAll();
    }

    public Map<String, Object> getStateAtTimestamp(Long productId, LocalDateTime parsedTimestamp) {
        // Query the database for the most recent history entry before or at the given timestamp
        Optional<ProductHistory> history = productHistoryRepository.findTopByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(productId, parsedTimestamp);

        // If no history is found, return an empty map or handle as needed
        if (history.isEmpty()) {
            return Collections.emptyMap();
        }

        // Map the ProductHistory entity to a state representation
        ProductHistory productHistory = history.get();
        Map<String, Object> state = new HashMap<>();
        state.put("productId", productHistory.getProduct().getProductId());
        state.put("name", productHistory.getName());
        state.put("price", productHistory.getPrice());
        state.put("stockQuantity", productHistory.getStockQuantity());
        state.put("validFrom", productHistory.getValidFrom());

        return state;
    }

    public Map<String, Object> getPricePeriods(Long productId) {
        List<ProductHistory> historyList = productHistoryRepository.findByProductIdOrderByValidFromAsc(productId);

        double maxPrice = Double.MIN_VALUE;
        double minPrice = Double.MAX_VALUE;
        LocalDateTime maxPriceStart = null, maxPriceEnd = null;
        LocalDateTime minPriceStart = null, minPriceEnd = null;

        for (int i = 0; i < historyList.size() - 1; i++) {
            ProductHistory current = historyList.get(i);

            if (current.getPrice() > maxPrice) {
                maxPrice = current.getPrice();
                maxPriceStart = current.getValidFrom();
                maxPriceEnd = historyList.get(i + 1).getValidFrom();
            } else if (current.getPrice() == maxPrice) {
                LocalDateTime maxPriceStartTemp = current.getValidFrom();
                LocalDateTime maxPriceEndTemp = historyList.get(i + 1).getValidFrom();
                long durationTemp = java.time.Duration.between(maxPriceStartTemp, maxPriceEndTemp).toSeconds();
                long duration = java.time.Duration.between(maxPriceStart, maxPriceEnd).toSeconds();

                if (durationTemp > duration) {
                    maxPriceStart = maxPriceStartTemp;
                    maxPriceEnd = maxPriceEndTemp;
                }
            }

            if (current.getPrice() < minPrice) {
                minPrice = current.getPrice();
                minPriceStart = current.getValidFrom();
                minPriceEnd = historyList.get(i + 1).getValidFrom();
            } else if (current.getPrice() == minPrice) {
                LocalDateTime minPriceStartTemp = current.getValidFrom();
                LocalDateTime minPriceEndTemp = historyList.get(i + 1).getValidFrom();
                long durationTemp = java.time.Duration.between(minPriceStartTemp, minPriceEndTemp).toSeconds();
                long duration = java.time.Duration.between(minPriceStart, minPriceEnd).toSeconds();

                if (durationTemp > duration) {
                    minPriceStart = minPriceStartTemp;
                    minPriceEnd = minPriceEndTemp;
                }
            }
        }

        // handle last case
        ProductHistory last = historyList.get(historyList.size() - 1);
        if (last.getPrice() > maxPrice) {
            maxPrice = last.getPrice();
            maxPriceStart = last.getValidFrom();
            maxPriceEnd = LocalDateTime.MAX;
        }

        if (last.getPrice() < minPrice) {
            minPrice = last.getPrice();
            minPriceStart = last.getValidFrom();
            minPriceEnd = LocalDateTime.MAX;
        }

        return Map.of(
                "maxPrice", maxPrice,
                "startTimeForMaxPrice", maxPriceStart,
                "endTimeForMaxPrice", maxPriceEnd,
                "minPrice", minPrice,
                "startTimeForMinPrice", minPriceStart,
                "endTimeForMinPrice", minPriceEnd
        );
    }
}
