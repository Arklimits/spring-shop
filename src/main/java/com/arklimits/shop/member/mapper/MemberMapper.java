package com.arklimits.shop.member.mapper;

import com.arklimits.shop.member.dto.MemberDTO;
import com.arklimits.shop.member.entity.Member;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDTO memberToMemberDto(Member member);
}