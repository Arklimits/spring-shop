package com.arklimits.shop.item.controller;

import com.arklimits.shop.item.S3Service;
import com.arklimits.shop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemRestController {

    private final ItemService itemService;
    private final S3Service s3Service;

    @DeleteMapping
    public ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/presigned-url")
    public String getPresignedUrl(@RequestParam String filename) {
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }
}
