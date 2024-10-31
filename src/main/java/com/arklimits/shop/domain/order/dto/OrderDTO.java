package com.arklimits.shop.domain.order.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private String title;
    private Integer price;
    private Integer quantity;
    private String memberDisplayName;
    private String imageUrl;
    private LocalDateTime orderDate;
}
