package com.finday.backend.bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "connected_user_bank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectedUserBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connected_user_bank_no")
    private Long connectedUserBankNo;

    @Column(name = "user_no", nullable = false)
    private Long userNo;

    @Column(name = "bank_name", length = 50, nullable = false)
    private String bankName;

    @Column(name = "is_connected")
    private Boolean isConnected;

    @Column(name = "connected_date", insertable = false, updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime connectedDate;

    @Column(name = "disconnected_date")
    private LocalDateTime disconnectedDate;
}
