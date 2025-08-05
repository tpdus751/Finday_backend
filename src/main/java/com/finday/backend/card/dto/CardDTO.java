package com.finday.backend.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {

    private String cardName;

    private String cardImgUrl;

    private String cardNumber;

    private String createdAt;

    private String bankName;

}
