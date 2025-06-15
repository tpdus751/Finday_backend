package com.finday.backend.account.service;

import com.finday.backend.account.dto.AccountCreateRequestDTO;
import com.finday.backend.account.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAllAccountsByUserNo(Long userNo);

    void createAccount(AccountCreateRequestDTO accountCreateRequestDTO);
}
