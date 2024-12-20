package com.arklimits.shop.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditItemDTO {

    @Schema(description = "아이템 ID", example = "1")
    private Long id;

    @Schema(description = "아이템 제목", example = "Sample Item")
    private String title;

    @Schema(description = "아이템 가격", example = "1000")
    private Integer price;

    @Schema(description = "아이템 이미지 URL", example = "null")
    private String imageUrl;
}
