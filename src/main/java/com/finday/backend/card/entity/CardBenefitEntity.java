package com.finday.backend.card.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_benefit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardBenefitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benefit_no")
    private Long benefitNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_no")
    private CardInfoEntity card;

    @Column(name = "benefit_type")
    private String benefitType; // ENUM("식비", "쇼핑", "교통", ...)

    @Column(name = "percentage")
    private int percentage;
}
