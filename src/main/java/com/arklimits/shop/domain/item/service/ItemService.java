package com.arklimits.shop.domain.item.service;

import com.arklimits.shop.domain.item.entity.Item;
import com.arklimits.shop.domain.item.repository.ItemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> listAllItems(Integer page) {
        return itemRepository.findAll(PageRequest.of(page - 1, 6));
    }

    public Page<Item> searchItems(String keywords, Integer page) {

        return itemRepository.findPageByTitleFullTextIndex(keywords, PageRequest.of(page - 1, 6));
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
        itemRepository.findById(id).ifPresent(item -> {
            item.setTitle(title);
            item.setPrice(price);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                item.setImageUrl(imageUrl);
            }
            itemRepository.save(item);
        });
    }


    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

}
