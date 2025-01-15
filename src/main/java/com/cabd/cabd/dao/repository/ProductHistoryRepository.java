package com.cabd.cabd.dao.repository;

import com.cabd.cabd.dao.model.Product;
import com.cabd.cabd.dao.model.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {
    Optional<ProductHistory> findTopByProductIdAndValidFromLessThanEqualOrderByValidFromDesc(Long productId, LocalDateTime validFrom);

    List<ProductHistory> findByProductIdOrderByValidFromAsc(Long productId);

    List<Product> findByProductId(Long productId);
}

