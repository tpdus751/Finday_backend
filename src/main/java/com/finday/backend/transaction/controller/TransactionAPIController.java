package com.finday.backend.transaction.controller;

import com.finday.backend.client.KftcTransactionClient;
import com.finday.backend.transaction.dto.TransactionForShowDTO;
import com.finday.backend.transaction.dto.TransactionSearchRequestDTO;
import com.finday.backend.transaction.dto.TransanctionInfoForCreateDTO;
import com.finday.backend.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionAPIController {

    private final KftcTransactionClient kftcTransactionClient;
    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(@RequestBody TransanctionInfoForCreateDTO transactionInfo) {

        System.out.println(transactionInfo.toString());

        boolean success = kftcTransactionClient.requestCreateTransactionToKftc(transactionInfo);

        return success
                ? ResponseEntity.ok("OK")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAIL");
    }

    @GetMapping("/list")
    public ResponseEntity<List<TransactionForShowDTO>> getUsersAllTransaction(
            @ModelAttribute TransactionSearchRequestDTO requestDto
    ) {
        System.out.println("트랜잭션 요청 들어옴");
        
        List<TransactionForShowDTO> result = transactionService.getTransactions(requestDto);

        System.out.println(result);

        return ResponseEntity.ok(result);
    }

}
