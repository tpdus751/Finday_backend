package com.finday.backend.card.service;

import com.finday.backend.card.dto.CardDTO;

import java.util.List;

public interface CardService {

    public List<String> getNotConnectedBankNames(Long userNo);

    List<CardDTO> connectSelectedBanks(String userSpecificNo, List<String> bankNames);

    void ConnectUserCard(String bankName, Long loginedUserNo, String cardName);

    List<String> getConnectedBankNames(Long userNo);
}
