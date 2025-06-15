package com.finday.backend.account.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_no")
    private Long accountNo; // PK

    @Column(name = "user_no", nullable = false)
    private Long userNo; // FK: users.user_no

    @Column(name = "bank_name", nullable = false, length = 50)
    private String bankName;

    @Column(name = "account_number", nullable = false, length = 20, unique = true)
    private String accountNumber;

    @Column(name = "alias", nullable = false, length = 30)
    private String alias;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "limit_amount")
    private Long limitAmount;

    @Column(name = "status", nullable = false, length = 10)
    private String status; // 예: ACTIVE, CLOSED 등 ENUM 대체 가능

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;
}
