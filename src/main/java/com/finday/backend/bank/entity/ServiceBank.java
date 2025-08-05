package com.finday.backend.bank.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_bank")
@Data
@NoArgsConstructor // ✅ 이게 반드시 있어야 JPA가 new ServiceBank() 가능
@AllArgsConstructor
@Builder
public class ServiceBank {

    @Id
    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Column(name = "bank_logo_img_url", length = 255)
    private String bankLogoImgUrl;
}

