package com.finday.backend.transaction.service;

import com.finday.backend.transaction.dto.TransactionForShowDTO;
import com.finday.backend.transaction.dto.TransactionSearchRequestDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionForShowDTO> getTransactions(TransactionSearchRequestDTO requestDto);
}
