package com.finday.backend.bank.repository;

import com.finday.backend.bank.entity.ConnectedUserBank;
import com.finday.backend.bank.entity.ServiceBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectedUserBankRepository extends JpaRepository<ConnectedUserBank, Long> {

    @Query("SELECT c.bankName FROM ConnectedUserBank c WHERE c.userNo = :userNo AND c.isConnected = true")
    List<String> findBankNamesByUserNoAndConnected(@Param("userNo") Long userNo);
}