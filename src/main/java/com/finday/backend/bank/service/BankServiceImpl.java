package com.finday.backend.bank.service;

import com.finday.backend.bank.entity.ConnectedUserBank;
import com.finday.backend.bank.entity.ServiceBank;
import com.finday.backend.bank.repository.ConnectedUserBankRepository;
import com.finday.backend.bank.repository.ServiceBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final ConnectedUserBankRepository connectedUserBankRepository;
    private final ServiceBankRepository serviceBankRepository;

    public List<ServiceBank> getConnectedBankNames(Long userNo) {
        List<String> connectedBankNames =  connectedUserBankRepository.findBankNamesByUserNoAndConnected(userNo);
        List<ServiceBank> connectedServiceBanks = new ArrayList<>();
        for (String bankName : connectedBankNames) {
            connectedServiceBanks.add(serviceBankRepository.findByBankName(bankName));
        }
        return connectedServiceBanks;
    }

    @Override
    public List<String> getNotConnectedBankNames(Long userNo) {
        List<String> allBankNames = serviceBankRepository.findAll()
                .stream()
                .map(ServiceBank::getBankName)
                .collect(Collectors.toList());

        List<String> connectedBankNames = connectedUserBankRepository.findBankNamesByUserNoAndConnected(userNo);

        // 전체 목록에서 연결된 은행 제거
        allBankNames.removeAll(connectedBankNames);

        return allBankNames;
    }

    @Override
    public void ConnectUserBank(String bankName, Long loginedUserNo) {
        ConnectedUserBank connectedUserBank = new ConnectedUserBank();
        connectedUserBank.setBankName(bankName);
        connectedUserBank.setUserNo(loginedUserNo);
        connectedUserBank.setIsConnected(true);

        connectedUserBankRepository.save(connectedUserBank);
    }

    @Override
    public List<String> getAllBanks() {
        List<String> allBankNames = serviceBankRepository.findAll()
                .stream()
                .map(ServiceBank::getBankName)
                .collect(Collectors.toList());

        return allBankNames;
    }
}