package com.finday.backend.card.service;

import com.finday.backend.card.dto.CardDTO;
import com.finday.backend.card.entity.CardInfoEntity;
import com.finday.backend.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public List<CardDTO> getCardsByBank(String bankName) {
        List<CardInfoEntity> cards = cardRepository.findAllWithBenefitsByBankName(bankName);
        return cards.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private CardDTO toDTO(CardInfoEntity card) {
        return CardDTO.builder()
                .cardNo(card.getCardNo())
                .bankNo(card.getBankNo())
                .cardName(card.getCardName())
                .cardIntro(card.getCardIntro())
                .cardType(card.getCardType())
                .isAvailableTransport(card.getIsAvailableTransport())
                .cardImgUrl(card.getCardImgUrl())
                .benefits(card.getBenefits().stream().map(b ->
                        CardDTO.BenefitDTO.builder()
                                .benefitType(b.getBenefitType())
                                .percentage(b.getPercentage())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }
}
