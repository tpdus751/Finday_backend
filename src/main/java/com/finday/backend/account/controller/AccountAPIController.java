package com.finday.backend.account.controller;


import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.service.AccountService;
import com.finday.backend.bank.service.BankService;
import com.finday.backend.client.KftcAccountsClient;
import com.finday.backend.account.dto.TransferRequestDTO;
import com.finday.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountAPIController {

    private final AccountService accountService;
    private final BankService bankService;
    private final UserService userService;
    private final KftcAccountsClient kftcAccountsClient;

    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam String userSpecificNo) {
        if (userSpecificNo == null) {
            return ResponseEntity.badRequest().build();
        }

        List<AccountDTO> accounts = accountService.getAccountsByUser(userSpecificNo);
        for (AccountDTO acc : accounts) {
            System.out.println(acc);
        }
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/selected")
    public List<AccountDTO> connectSelectedBanks(@RequestBody Map<String, Object> body) {
        String userSpecificNo = (String) body.get("userSpecificNo");
        List<String> bankNames = (List<String>) body.get("bankNames");
        List<AccountDTO> selectedAccountList =  accountService.connectSelectedBanks(userSpecificNo, bankNames);

        Long loginedUserNo = userService.getUserNoByUserSpecificNo(userSpecificNo);

        if (selectedAccountList.size() != 0) {

            for (AccountDTO selectedAccount : selectedAccountList) {
                bankService.ConnectUserBank(selectedAccount.getBankName(), loginedUserNo);
            }
        }

        return selectedAccountList;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDTO request) {
        try {
            boolean success = kftcAccountsClient.requestTransferToKftc(request);
            return success
                    ? ResponseEntity.ok("OK")
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAIL");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAIL: " + e.getMessage());
        }
    }
}