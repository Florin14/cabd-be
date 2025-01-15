package com.cabd.cabd.dao.repository;

import com.cabd.cabd.dao.model.Product;
import com.cabd.cabd.dao.model.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Long> {

}

