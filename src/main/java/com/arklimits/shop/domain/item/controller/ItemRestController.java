package com.arklimits.shop.domain.item.controller;

import com.arklimits.shop.domain.item.S3Service;
import com.arklimits.shop.domain.item.dto.CreateItemDTO;
import com.arklimits.shop.domain.item.dto.EditItemDTO;
import com.arklimits.shop.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemRestController {

    private final ItemService itemService;
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody CreateItemDTO createItemDTO) {
        itemService.saveItem(createItemDTO.getTitle(), createItemDTO.getPrice(),
            createItemDTO.getImageUrl());

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> editItem(
        @RequestBody EditItemDTO editItemDto) {

        System.out.println(editItemDto);
        itemService.editItem(editItemDto.getId(), editItemDto.getTitle(), editItemDto.getPrice(),
            editItemDto.getImageUrl());

        return ResponseEntity.ok().build();
    }

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
