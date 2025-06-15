package com.finday.backend.account.dto;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long fromAccountNo;
    private String toAccountNumber;
    private String toBank;
    private Long userNo;
    private Long amount;
}
