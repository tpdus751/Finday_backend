package com.finday.backend.account.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private Long accountNo;
    private String bankName;
    private String accountNumber;
    private String alias;
    private Long balance;
    private String status;
    private LocalDateTime createdAt;
}