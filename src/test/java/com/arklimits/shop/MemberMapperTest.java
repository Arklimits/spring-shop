package com.arklimits.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arklimits.shop.member.dto.MemberDto;
import com.arklimits.shop.member.entity.Member;
import com.arklimits.shop.member.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MemberMapperTest {

    private final MemberMapper memberMapper = Mappers.getMapper(MemberMapper.class);

    @Test
    public void testMemberToMemberDto() {
        Member member = new Member();
        member.setId(1L);
        member.setUsername("testuser");
        member.setDisplayName("Test User");

        System.out.println(member);

        MemberDto memberDto = memberMapper.memberToMemberDto(member);

        System.out.println(memberDto);

        assertEquals(member.getId(), memberDto.getId());
        assertEquals(member.getUsername(), memberDto.getUsername());
        assertEquals(member.getDisplayName(), memberDto.getDisplayName());
    }
}
