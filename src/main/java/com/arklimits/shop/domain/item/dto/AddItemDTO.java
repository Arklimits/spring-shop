package com.arklimits.shop.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddItemDTO {

    private String title;
    private Integer price;
    private String imageUrl;
}
