package com.finday.backend.card.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "connected_user_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectedUserCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connected_user_card_no")
    private Long connectedUserCardNo;

    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "card_name", nullable = false, length = 100)
    private String cardName;

    @Column(name = "bank_name", length = 50, nullable = false)
    private String bankName;

    @Column(name = "is_connected", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isConnected = true;

    @Column(name = "connected_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime connectedDate = LocalDateTime.now();

    @Column(name = "disconnected_date")
    private LocalDateTime disconnectedDate;
}
