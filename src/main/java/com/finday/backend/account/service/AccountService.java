package com.finday.backend.account.service;

import com.finday.backend.account.dto.AccountCreateRequestDTO;
import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.dto.TransferRequestDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAllAccountsByUserNo(Long userNo);

    void createAccount(AccountCreateRequestDTO accountCreateRequestDTO);

    AccountDTO getAccountByNo(Long accountNo);

    String findAccountOwner(String bankName, String accountNumber);

    void transfer(TransferRequestDTO dto);
}
