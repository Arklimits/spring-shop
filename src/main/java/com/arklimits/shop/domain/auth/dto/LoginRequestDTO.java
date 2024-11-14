package com.arklimits.shop.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequestDTO {

    @Schema(description = "사용자 아이디", example = "asdf")
    private String username;

    @Schema(description = "사용자 비밀번호", example = "asdf")
    private String password;
}