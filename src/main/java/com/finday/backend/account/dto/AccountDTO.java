package com.finday.backend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    private String accountNumber;
    private String alias;
    private Long balance;
    private String createdAt;
    private String bankName;
    private String accountName;
    private String bankLogoImgUrl;
}
