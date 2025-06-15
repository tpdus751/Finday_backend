package com.finday.backend.card.repository;

import com.finday.backend.card.entity.CardInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardInfoEntity, Long> {

    @Query("SELECT c FROM CardInfoEntity c " +
            "JOIN FETCH c.benefits b " +
            "JOIN BankInfoEntity bnk ON c.bankNo = bnk.bankNo " +
            "WHERE bnk.bankName = :bankName")
    List<CardInfoEntity> findAllWithBenefitsByBankName(@Param("bankName") String bankName);
}
