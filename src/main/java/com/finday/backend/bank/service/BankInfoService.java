package com.finday.backend.bank.service;

import com.finday.backend.bank.dto.BankDTO;

import java.util.List;

public interface BankInfoService {
    List<BankDTO> getAllBanks();
}
