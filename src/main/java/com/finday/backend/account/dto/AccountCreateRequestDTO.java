package com.finday.backend.account.dto;

import lombok.Data;

// AccountCreateRequestDto.java
@Data
public class AccountCreateRequestDTO {
    private Long userNo;
    private String bankName;
    private String alias;
    private boolean cardRequested;
    private Long cardNo; // nullable
    private Long accountLimit; // nullable, 단위: 원
}
