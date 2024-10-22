package com.arklimits.shop.member.mapper;

import com.arklimits.shop.member.dto.MemberDTO;
import com.arklimits.shop.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "displayName", target = "displayName")
    MemberDTO memberToMemberDto(Member member);
}