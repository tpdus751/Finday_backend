package com.finday.backend.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionForShowDTO {

    private String transactionName;

    private String transactionType;

    private String transactionCategory;

    private String fromAccountDisplay;

    private String toAccountDisplay;

    private Long amount;

    private String createdAt;

    private String accountName;

    private String bankName;

    private String cardNumber;

    private String paidCardName;
}
