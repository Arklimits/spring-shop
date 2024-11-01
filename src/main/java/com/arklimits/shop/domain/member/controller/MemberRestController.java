package com.arklimits.shop.domain.member.controller;

import com.arklimits.shop.domain.member.dto.MemberDTO;
import com.arklimits.shop.domain.member.dto.RegisterMemberDTO;
import com.arklimits.shop.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberRestController {

    private static final Logger logger = LoggerFactory.getLogger(MemberRestController.class);
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> addMember(@RequestBody RegisterMemberDTO registerMemberDTO) {
        if (memberService.isUsernameDuplicate(registerMemberDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용 중인 사용자 이름입니다.");
        } else {
            memberService.addMember(registerMemberDTO);
            logger.info("새 사용자 등록: {}", registerMemberDTO);
            return ResponseEntity.ok("등록완료.");
        }
    }

    @GetMapping("/check-username")
    public ResponseEntity<Void> checkUsernameDuplicate(@RequestParam String username) {
        boolean isDuplicate = memberService.isUsernameDuplicate(username);
        return isDuplicate ? ResponseEntity.status(HttpStatus.CONFLICT).build()
            : ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getUser(@PathVariable Long id) {
        MemberDTO user = memberService.getUserById(id);
        return user != null ? ResponseEntity.ok(user)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
