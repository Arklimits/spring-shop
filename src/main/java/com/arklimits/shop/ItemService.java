package com.arklimits.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price){
        Item item = new Item(title, price);
        itemRepository.save(item);
    }

    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }
}
