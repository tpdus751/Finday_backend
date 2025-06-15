package com.finday.backend.card.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CardDTO {

    private Long cardNo;
    private Long bankNo;
    private String cardName;
    private String cardIntro;
    private String cardType; // 체크카드 / 신용카드
    private Boolean isAvailableTransport;
    private String cardImgUrl;

    private List<BenefitDTO> benefits;

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class BenefitDTO {
        private String benefitType;
        private int percentage;
    }
}

