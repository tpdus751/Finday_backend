package com.finday.backend.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransanctionInfoForCreateDTO {

    private String merchant;

    private Long amount;

    private String category;

    private String bankName;

    private String methodType;

    private String methodId;

    private String userSpecificNo;
}
