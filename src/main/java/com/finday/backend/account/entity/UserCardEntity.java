package com.finday.backend.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_card_no", nullable = false, updatable = false)
    private Long userCardNo;

    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "account_no", nullable = false)
    private Long accountNo;

    @Column(name = "card_no", nullable = false)
    private Long cardNo;

    @CreationTimestamp
    @Column(name = "issue_date", nullable = false, updatable = false)
    private LocalDateTime issuedDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "terminated_date")
    private LocalDateTime terminatedDate;
}