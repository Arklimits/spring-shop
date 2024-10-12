package com.arklimits.shop.item.service;

import com.arklimits.shop.item.entity.Item;
import com.arklimits.shop.item.repository.ItemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

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

    public void editItem(Long id, String title, Integer price) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setTitle(title);
            item.setPrice(price);
            itemRepository.save(item);
        }
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
