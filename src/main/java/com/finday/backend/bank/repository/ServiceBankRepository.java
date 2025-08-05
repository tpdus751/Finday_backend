package com.finday.backend.bank.repository;

import com.finday.backend.bank.entity.ServiceBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceBankRepository extends JpaRepository<ServiceBank, String> {
    ServiceBank findByBankName(String bankName);
}
