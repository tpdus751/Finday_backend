package com.finday.backend.account.repository;

import com.finday.backend.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserNo(Long userNo);

    @Query("SELECT a FROM AccountEntity a WHERE a.bankName = :bankName AND a.accountNumber = :accountNumber")
    Optional<AccountEntity> findAccountByBankAndAccountNumber(@Param("bankName") String bankName,
                                                         @Param("accountNumber") String accountNumber);
}
