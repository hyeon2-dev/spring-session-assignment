package com.example.springsessionassignment.member.controller;

import com.example.springsessionassignment.member.dto.MemberRequestDto;
import com.example.springsessionassignment.member.dto.MemberResponseDto;
import com.example.springsessionassignment.member.dto.MemberUpdateRequestDto;
import com.example.springsessionassignment.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers () {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember (@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @PutMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(memberService.updateMember(memberId, dto));
    }

    @DeleteMapping("/members/{memberId}")
    public void deleteMember(@PathVariable Long memberId){
        memberService.deleteMember(memberId);
    }
}
