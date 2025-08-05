package com.finday.backend.bank.service;

import com.finday.backend.bank.entity.ServiceBank;

import java.util.List;

public interface BankService {
    public List<ServiceBank> getConnectedBankNames(Long userNo);
    public List<String> getNotConnectedBankNames(Long userNo);

    void ConnectUserBank(String bankName, Long loginedUserNo);

    List<String> getAllBanks();
}
