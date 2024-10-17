package com.arklimits.shop.item.repository;

import com.arklimits.shop.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    Page<Item> findPageByTitleFullTextIndex(String keyword, Pageable pageable);
}
