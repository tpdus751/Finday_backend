package com.finday.backend.account.service;

import com.finday.backend.client.KftcAccountsClient;
import com.finday.backend.account.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final KftcAccountsClient kftcAccountsClient;

    public List<AccountDTO> getAccountsByUser(String userSpecificNo) {
        return kftcAccountsClient.getAllAccounts(userSpecificNo);
    }

    @Override
    public List<AccountDTO> connectSelectedBanks(String userSpecificNo, List<String> bankNames) {
        return kftcAccountsClient.getSelectedAccounts(userSpecificNo, bankNames);
    }
}