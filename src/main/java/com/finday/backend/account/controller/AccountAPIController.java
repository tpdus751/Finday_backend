package com.finday.backend.account.controller;


import com.finday.backend.account.dto.AccountCreateRequestDTO;
import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.dto.TransferRequestDTO;
import com.finday.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AccountAPIController {

    private final AccountService accountService;

    // ✅ 사용자 번호 기반 계좌 전체 조회 (해지 포함)
    @GetMapping("/all")
    public List<AccountDTO> getAllAccounts(@RequestParam("userNo") Long userNo) {
        System.out.println("요청 번호 : " + userNo);
        return accountService.findAllAccountsByUserNo(userNo);
    }

    @GetMapping("/one")
    public ResponseEntity<AccountDTO> getAccountByAccountNo(@RequestParam Long accountNo) {
        AccountDTO account = accountService.getAccountByNo(accountNo);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/owner")
    public ResponseEntity<Map<String, String>> getAccountOwner(
            @RequestParam String bankName,
            @RequestParam String accountNumber) {

        String ownerName = accountService.findAccountOwner(bankName, accountNumber);
        Map<String, String> response = new HashMap<>();
        response.put("name", ownerName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        try {
            accountService.createAccount(accountCreateRequestDTO);
            return ResponseEntity.ok("계좌 개설 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류 발생: " + e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO dto) {
        accountService.transfer(dto);
        return ResponseEntity.ok("이체가 완료되었습니다.");
    }

}