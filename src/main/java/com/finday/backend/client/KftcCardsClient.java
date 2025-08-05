package com.finday.backend.client;

import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.card.dto.CardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KftcCardsClient {

    private final WebClient webClient = WebClient.create();

    @Value("${kftc.api.url}")  // application.yml에서 설정
    private String kftcApiUrl;

    public List<CardDTO> getSelectedCards(String userSpecificNo, List<String> bankNames) {
        List<CardDTO> checkedCardsFromBankServer = new ArrayList<>();

        Map<String, String> bankMap = new HashMap<>();
        bankMap.put("국민은행", "kookmin");
        bankMap.put("신한은행", "shinhan");
        bankMap.put("하나은행", "hana");
        bankMap.put("우리은행", "woori");
        bankMap.put("농협은행", "nh");
        bankMap.put("SC제일은행", "sc");
        bankMap.put("카카오뱅크", "kakao");
        bankMap.put("케이뱅크", "k");
        bankMap.put("토스뱅크", "toss");

        for (String bankName : bankNames) {
            try {
                String bankCode = bankMap.get(bankName);
                String fullUrl = kftcApiUrl + "/gateway/cards/list?bankName=" + bankCode + "&userSpecificNo=" + userSpecificNo;

                List<CardDTO> cards = webClient.get()
                        .uri(fullUrl)  // ← 문자열 직접 넣기
                        .retrieve()
                        .bodyToFlux(CardDTO.class)
                        .collectList()
                        .block();

                // 계좌가 1개 이상 존재할 경우, 연결된 은행으로 간주
                if (cards != null && !cards.isEmpty()) {
                    for (CardDTO card : cards) {
                        checkedCardsFromBankServer.add(card);
                    }
                }
            } catch (Exception e) {
                System.err.println("카드 연동 실패: " + bankName + " - " + e.getMessage());
            }
        }

        return checkedCardsFromBankServer;
    }

}
