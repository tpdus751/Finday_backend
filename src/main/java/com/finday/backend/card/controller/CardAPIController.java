package com.finday.backend.card.controller;

import com.finday.backend.card.dto.CardDTO;
import com.finday.backend.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.Card;
import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CardAPIController {

    private final CardService cardService;

    // ✅ 은행명으로 카드 목록 조회
    @GetMapping("/by-bank")
    public List<CardDTO> getCardsByBank(@RequestParam String bankName) {
        List<CardDTO> cardListGroupByBank =  cardService.getCardsByBank(bankName);
        for (CardDTO card : cardListGroupByBank) {
            System.out.println(card);
        }
        return cardListGroupByBank;
    }
}
