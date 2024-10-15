package com.arklimits.shop.item.service;

import com.arklimits.shop.item.entity.Item;
import com.arklimits.shop.item.repository.ItemRepository;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> listAllItems(Integer page) {
        return itemRepository.findAll(PageRequest.of(page - 1, 5));
    }

    public Page<Item> searchItems(String keywords, Integer page) {
        String encodedKeywords = UriUtils.encode(keywords, StandardCharsets.UTF_8);
        return itemRepository.findPageByTitleContains(keywords, PageRequest.of(page - 1, 5));
    }

    public void saveItem(String title, Integer price, String imageUrl) {
        System.out.println(imageUrl);
        if (imageUrl.isBlank()) {
            imageUrl = "https://placehold.co/400";
        }
        Item item = new Item(title, price, imageUrl);
        itemRepository.save(item);
    }

    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public void editItem(Long id, String title, Integer price, String imageUrl) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setTitle(title);
            item.setPrice(price);
            item.setImageUrl(imageUrl);
            itemRepository.save(item);
        }
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}
