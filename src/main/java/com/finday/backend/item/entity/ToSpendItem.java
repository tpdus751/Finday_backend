package com.finday.backend.item.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "to_spend_items")
@Getter
@Setter
public class ToSpendItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_no")
    private Long itemNo;

    @Column(name = "merchant_name", nullable = false, length = 100)
    private String merchantName;

    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;

    @Column(name = "price", nullable = false)
    private Long price = 0L;

    @Column(name = "category", nullable = false, length = 30)
    private String category; // enum이지만 Java에서는 String으로 매핑

    @Column(name = "img_url", length = 255)
    private String imgUrl;
}
