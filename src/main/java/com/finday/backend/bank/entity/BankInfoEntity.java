package com.finday.backend.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_info")
public class BankInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_no")
    private Long bankNo;

    @Column(name = "bank_type", nullable = false, length = 50)
    private String bankType;

    @Column(name = "bank_name", nullable = false, length = 100)
    private String bankName;
}