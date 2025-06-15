package com.finday.backend.card.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "card_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_no")
    private Long cardNo;

    @Column(name = "bank_no")
    private Long bankNo;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_intro", columnDefinition = "TEXT")
    private String cardIntro;

    @Column(name = "card_type")
    private String cardType; // "체크카드" or "신용카드"

    @Column(name = "is_available_transport")
    private Boolean isAvailableTransport;

    @Column(name = "card_img_url")
    private String cardImgUrl;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CardBenefitEntity> benefits = new ArrayList<>();
}
