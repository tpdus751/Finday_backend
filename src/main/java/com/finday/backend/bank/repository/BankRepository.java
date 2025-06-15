package com.finday.backend.bank.repository;

import com.finday.backend.bank.entity.BankInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankInfoEntity, Long> {

}
