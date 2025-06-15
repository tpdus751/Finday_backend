package com.finday.backend.bank.controller;

import com.finday.backend.bank.dto.BankDTO;
import com.finday.backend.bank.entity.BankInfoEntity;
import com.finday.backend.bank.service.BankInfoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BankInfoAPIController {

    private final BankInfoService bankInfoService;

    public BankInfoAPIController(BankInfoService bankInfoService) {
        this.bankInfoService = bankInfoService;
    }

    @GetMapping("/list")
    public List<BankDTO> getBankList() {
        return bankInfoService.getAllBanks();
    }

}
