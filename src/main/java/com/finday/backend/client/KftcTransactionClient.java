package com.finday.backend.client;

import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.bank.entity.ServiceBank;
import com.finday.backend.bank.service.BankService;
import com.finday.backend.transaction.dto.TransactionForShowDTO;
import com.finday.backend.transaction.dto.TransactionSearchRequestDTO;
import com.finday.backend.transaction.dto.TransanctionInfoForCreateDTO;
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
public class KftcTransactionClient {

    private final WebClient webClient = WebClient.create();
    private final BankService bankService;


    @Value("${kftc.api.url}")  // application.yml에서 설정
    private String kftcApiUrl;


    public boolean requestCreateTransactionToKftc(TransanctionInfoForCreateDTO transactionInfo) {
        String url = kftcApiUrl + "/gateway/pay/intergrated-method";

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

        transactionInfo.setBankName(bankMap.get(transactionInfo.getBankName()));

        try {
            String result = webClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")  // ✅ 명시 추가
                    .bodyValue(transactionInfo)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return "OK".equals(result);
        } catch (Exception e) {
            System.err.println("금융결제원 결제 요청 실패: " + e.getMessage());
            return false;
        }

    }

    public List<TransactionForShowDTO> getTransactionList(TransactionSearchRequestDTO requestDto) {

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

        List<TransactionForShowDTO> allTransactions = new ArrayList<>();

        if (requestDto.getBankName() == null) {
            try {
                List<String> bankNames = bankService.getConnectedBankNames(requestDto.getUserNo()).stream()
                        .map(ServiceBank::getBankName)
                        .toList(); // Java 16 이상일 경우

                System.out.println(bankNames);

                for (String bankName : bankNames) {

                    String url = kftcApiUrl + "/gateway/transaction";

                    System.out.println(bankName + "차례 입니다");
                    
                    try {
                        String bankCode = bankMap.get(bankName);

                        if (requestDto.getFilterType().equals("30days")) {
                            url += "/latest30days?bankName=" + bankCode + "&userSpecificNo=" + requestDto.getUserSpecificNo();
                        } else {
                            url += "/month?bankName=" + bankCode + "&userSpecificNo=" + requestDto.getUserSpecificNo() + "&month=" + requestDto.getMonth();
                        }

                        List<TransactionForShowDTO> transactions = webClient.get()
                                .uri(url)  // ← 문자열 직접 넣기
                                .retrieve()
                                .bodyToFlux(TransactionForShowDTO.class)
                                .collectList()
                                .block();

                        System.out.println(bankName + " : " + transactions);

                        for (TransactionForShowDTO transaction : transactions) {
                            allTransactions.add(transaction);
                        }

                    } catch (Exception e) {
                        System.err.println("거래 내역 조회 실패: " + bankName + " - " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("연동된 계좌 없음: " + e.getMessage());
            }
        } else {
            String url = kftcApiUrl + "/gateway/transaction";

            try {
                String bankCode = bankMap.get(requestDto.getBankName());

                if (requestDto.getFilterType().equals("30days")) {
                    url += "/latest30days?bankName=" + bankCode + "&userSpecificNo=" + requestDto.getUserSpecificNo();
                } else {
                    url += "/month?bankName=" + bankCode + "&userSpecificNo=" + requestDto.getUserSpecificNo() + "&month=" + requestDto.getMonth();
                }

                List<TransactionForShowDTO> transactions = webClient.get()
                        .uri(url)  // ← 문자열 직접 넣기
                        .retrieve()
                        .bodyToFlux(TransactionForShowDTO.class)
                        .collectList()
                        .block();

                for (TransactionForShowDTO transaction : transactions) {
                    allTransactions.add(transaction);
                }

            } catch (Exception e) {
                System.err.println("거래 내역 조회 실패: " + requestDto.getBankName() + " - " + e.getMessage());
            }
        }

        return allTransactions;
    }
}
