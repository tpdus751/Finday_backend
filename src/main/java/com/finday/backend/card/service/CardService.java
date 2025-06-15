package com.finday.backend.card.service;

import com.finday.backend.card.dto.CardDTO;

import java.util.List;

public interface CardService {
    List<CardDTO> getCardsByBank(String bankName);
}
