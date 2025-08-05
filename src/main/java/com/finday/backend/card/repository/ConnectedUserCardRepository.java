package com.finday.backend.card.repository;

import com.finday.backend.card.entity.ConnectedUserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectedUserCardRepository extends JpaRepository<ConnectedUserCard, Long> {

    @Query("SELECT c.bankName FROM ConnectedUserCard c WHERE c.userNo = :userNo AND c.isConnected = true")
    List<String> findBankNamesByUserNoAndConnected(@Param("userNo") Long userNo);

}
