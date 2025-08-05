package com.finday.backend.client;

import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.dto.TransferRequestDTO;
import com.finday.backend.bank.entity.ServiceBank;
import com.finday.backend.bank.service.BankService;
import com.finday.backend.transaction.dto.TransactionForShowDTO;
import com.finday.backend.transaction.dto.TransactionSearchRequestDTO;
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
public class KftcAccountsClient {

    private final WebClient webClient = WebClient.create();

    @Value("${kftc.api.url}")  // application.yml에서 설정
    private String kftcApiUrl;

    public List<AccountDTO> getAllAccounts(String userSpecificNo) {
        return webClient.get()
                .uri(kftcApiUrl + "/gateway/accounts/all?userSpecificNo={userSpecificNo}", userSpecificNo)
                .retrieve()
                .bodyToFlux(AccountDTO.class)
                .collectList()
                .block(); // 동기 방식
    }

    public List<AccountDTO> getSelectedAccounts(String userSpecificNo, List<String> bankNames) {
        List<AccountDTO> checkedAccountsFromBankServer = new ArrayList<>();

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
                String fullUrl = kftcApiUrl + "/gateway/accounts/list?bankName=" + bankCode + "&userSpecificNo=" + userSpecificNo;

                List<AccountDTO> accounts = webClient.get()
                        .uri(fullUrl)  // ← 문자열 직접 넣기
                        .retrieve()
                        .bodyToFlux(AccountDTO.class)
                        .collectList()
                        .block();

                // 계좌가 1개 이상 존재할 경우, 연결된 은행으로 간주
                if (accounts != null && !accounts.isEmpty()) {
                    for (AccountDTO account : accounts) {
                        checkedAccountsFromBankServer.add(account);
                    }
                }
            } catch (Exception e) {
                System.err.println("은행 연동 실패: " + bankName + " - " + e.getMessage());
            }
        }

        return checkedAccountsFromBankServer;
    }

    public boolean requestTransferToKftc(TransferRequestDTO request) {
        Map<String, String> bankMap = Map.of(
                "국민은행", "kookmin",
                "신한은행", "shinhan",
                "하나은행", "hana",
                "우리은행", "woori",
                "농협은행", "nh",
                "SC제일은행", "sc",
                "카카오뱅크", "kakao",
                "케이뱅크", "k",
                "토스뱅크", "toss"
        );
        request.setFromBankName(bankMap.get(request.getFromBankName()));
        request.setToBankName(bankMap.get(request.getToBankName()));
        try {
            String url = kftcApiUrl + "/gateway/transfer";

            String result = webClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")  // ✅ 명시 추가
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return "OK".equals(result);
        } catch (Exception e) {
            System.err.println("금융결제원 이체 요청 실패: " + e.getMessage());
            return false;
        }
    }
}