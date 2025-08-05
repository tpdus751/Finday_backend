package com.finday.backend.bank.controller;

import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.service.AccountService;
import com.finday.backend.bank.entity.ServiceBank;
import com.finday.backend.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000" }, allowCredentials = "true")
public class BankController {

    private final BankService bankService;
    private final AccountService accountService;

    @GetMapping("/connected")
    public ResponseEntity<List<AccountDTO>> getConnectedBanks(@RequestParam Long userNo, @RequestParam String userSpecificNo) {
        List<ServiceBank> connectedBanks = bankService.getConnectedBankNames(userNo);

        List<String> bankNames = new ArrayList<>();

        Map<String, String> findBankLogo = new HashMap<>();

        for (ServiceBank bank : connectedBanks) {
            bankNames.add(bank.getBankName());

            findBankLogo.put(bank.getBankName(), bank.getBankLogoImgUrl());
        }

        if (bankNames.size() != 0) {
            List<AccountDTO> accountDTOList = accountService.connectSelectedBanks(userSpecificNo, bankNames);
            for (AccountDTO account : accountDTOList) {
                account.setBankLogoImgUrl(findBankLogo.get(account.getBankName()));
            }
            return ResponseEntity.ok(accountDTOList);
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/not_connected")
    public ResponseEntity<List<String>> getNotConnectdAll(@RequestParam Long userNo) {
        List<String> bankNames = bankService.getNotConnectedBankNames(userNo);
        return ResponseEntity.ok(bankNames);
    }

    // ✅ 서비스에 등록된 은행 목록 조회
    @GetMapping("/list")
    public List<String> getAllBanks() {
        return bankService.getAllBanks();
    }

    @GetMapping("/connected_bankNames")
    public List<ServiceBank> getConnectedBankNames(@RequestParam Long userNo) {
        List<ServiceBank> connectedBankNames = bankService.getConnectedBankNames(userNo);

        return connectedBankNames;
    }
}

