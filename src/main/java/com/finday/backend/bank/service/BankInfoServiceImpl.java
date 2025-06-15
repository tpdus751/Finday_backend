package com.finday.backend.bank.service;

import com.finday.backend.bank.dto.BankDTO;
import com.finday.backend.bank.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankInfoServiceImpl implements BankInfoService {
    private final BankRepository bankInfoRepository;

    public BankInfoServiceImpl(BankRepository bankInfoRepository) {
        this.bankInfoRepository = bankInfoRepository;
    }

    public List<BankDTO> getAllBanks() {
        return bankInfoRepository.findAll().stream()
                .map(bank -> new BankDTO(bank.getBankName(), bank.getBankType()))
                .collect(Collectors.toList());
    }
}
