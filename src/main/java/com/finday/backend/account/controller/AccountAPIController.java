package com.finday.backend.account.controller;


import com.finday.backend.account.dto.AccountCreateRequestDTO;
import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}