package com.finday.backend.card.controller;

import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.service.AccountService;
import com.finday.backend.card.dto.CardDTO;
import com.finday.backend.card.service.CardService;
import com.finday.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @GetMapping("/connected")
    public ResponseEntity<List<CardDTO>> getConnectedBanks(@RequestParam Long userNo, @RequestParam String userSpecificNo) {
        List<String> bankNames = cardService.getConnectedBankNames(userNo);

        if (bankNames.size() != 0) {
            return ResponseEntity.ok(cardService.connectSelectedBanks(userSpecificNo, bankNames));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/not_connected")
    public ResponseEntity<List<String>> getNotConnectdAll(@RequestParam Long userNo) {
        List<String> bankNames = cardService.getNotConnectedBankNames(userNo);
        return ResponseEntity.ok(bankNames);
    }

    @PostMapping("/selected")
    public List<CardDTO> connectSelectedBanks(@RequestBody Map<String, Object> body) {
        String userSpecificNo = (String) body.get("userSpecificNo");
        List<String> bankNames = (List<String>) body.get("bankNames");
        List<CardDTO> selectedCardList =  cardService.connectSelectedBanks(userSpecificNo, bankNames);

        Long loginedUserNo = userService.getUserNoByUserSpecificNo(userSpecificNo);

        if (selectedCardList.size() != 0) {

            for (CardDTO selectedCard : selectedCardList) {
                cardService.ConnectUserCard(selectedCard.getBankName(), loginedUserNo, selectedCard.getCardName());
            }
        }

        return selectedCardList;
    }
}
