package com.finday.backend.card.service;

import com.finday.backend.bank.entity.ConnectedUserBank;
import com.finday.backend.bank.entity.ServiceBank;
import com.finday.backend.bank.repository.ServiceBankRepository;
import com.finday.backend.card.dto.CardDTO;
import com.finday.backend.card.entity.ConnectedUserCard;
import com.finday.backend.card.repository.ConnectedUserCardRepository;
import com.finday.backend.client.KftcAccountsClient;
import com.finday.backend.client.KftcCardsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final ConnectedUserCardRepository connectedUserCardRepository;
    private final ServiceBankRepository serviceBankRepository;
    private final KftcCardsClient kftcCardsClient;

    @Override
    public List<String> getNotConnectedBankNames(Long userNo) {
        List<String> allBankNames = serviceBankRepository.findAll()
                .stream()
                .map(ServiceBank::getBankName)
                .collect(Collectors.toList());

        List<String> connectedBankNames = connectedUserCardRepository.findBankNamesByUserNoAndConnected(userNo);

        // 전체 목록에서 연결된 은행 제거
        allBankNames.removeAll(connectedBankNames);

        return allBankNames;
    }

    @Override
    public List<CardDTO> connectSelectedBanks(String userSpecificNo, List<String> bankNames) {
        return kftcCardsClient.getSelectedCards(userSpecificNo, bankNames);
    }

    @Override
    public void ConnectUserCard(String bankName, Long loginedUserNo, String cardName) {
        ConnectedUserCard connectedUserCard = new ConnectedUserCard();
        connectedUserCard.setBankName(bankName);
        connectedUserCard.setUserNo(loginedUserNo);
        connectedUserCard.setCardName(cardName);
        connectedUserCard.setIsConnected(true);

        connectedUserCardRepository.save(connectedUserCard);
    }

    @Override
    public List<String> getConnectedBankNames(Long userNo) {
        return connectedUserCardRepository.findBankNamesByUserNoAndConnected(userNo);
    }

}
