package com.finday.backend.transaction.service;

import com.finday.backend.client.KftcAccountsClient;
import com.finday.backend.client.KftcTransactionClient;
import com.finday.backend.transaction.dto.TransactionForShowDTO;
import com.finday.backend.transaction.dto.TransactionSearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final KftcTransactionClient kftcTransactionClient;

    @Override
    public List<TransactionForShowDTO> getTransactions(TransactionSearchRequestDTO requestDto) {

        List<TransactionForShowDTO> transactionList = kftcTransactionClient.getTransactionList(requestDto);

        // createdAt 기준 내림차순 정렬 (최신순)
        transactionList.sort((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()));

        return transactionList;
    }
}
