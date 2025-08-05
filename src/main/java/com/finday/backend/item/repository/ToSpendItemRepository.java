package com.finday.backend.item.repository;

import com.finday.backend.item.entity.ToSpendItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToSpendItemRepository extends JpaRepository<ToSpendItem, Long> {
    List<ToSpendItem> findByCategoryAndMerchantName(String category, String merchantName);

    @Query("SELECT DISTINCT t.merchantName FROM ToSpendItem t WHERE t.category = :category")
    List<String> findDistinctMerchantNamesByCategory(@Param("category") String category);
}

