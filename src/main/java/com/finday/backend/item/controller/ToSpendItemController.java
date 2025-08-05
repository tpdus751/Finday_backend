package com.finday.backend.item.controller;

import com.finday.backend.item.entity.ToSpendItem;
import com.finday.backend.item.repository.ToSpendItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ToSpendItemController {

    private final ToSpendItemRepository toSpendItemRepository;

    @GetMapping("/list")
    public ResponseEntity<List<ToSpendItem>> getItemsByCategoryAndMerchant(
            @RequestParam String category,
            @RequestParam String merchant
    ) {
        List<ToSpendItem> items = toSpendItemRepository.findByCategoryAndMerchantName(category, merchant);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/merchants")
    public ResponseEntity<List<String>> getMerchantsByCategory(@RequestParam String category) {
        List<String> merchants = toSpendItemRepository.findDistinctMerchantNamesByCategory(category);
        return ResponseEntity.ok(merchants);
    }
}