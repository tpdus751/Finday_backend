package com.finday.backend.account.service;

import com.finday.backend.account.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    public List<AccountDTO> getAccountsByUser(String userSpecificNo);

    List<AccountDTO> connectSelectedBanks(String userSpecificNo, List<String> bankNames);
}
