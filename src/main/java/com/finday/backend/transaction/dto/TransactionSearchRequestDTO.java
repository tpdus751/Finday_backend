package com.finday.backend.transaction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSearchRequestDTO {
        private Long userNo;
        private String userSpecificNo;
        private String bankName;       // nullable
        private String filterType;     // "30days" or "month"
        private String month;          // e.g. "2025-07"
}
