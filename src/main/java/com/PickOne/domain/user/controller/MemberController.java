package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.MemberDto.*;
import com.PickOne.domain.user.service.MemberService;
import com.PickOne.global.exception.SuccessCode;
import com.PickOne.global.exception.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<BaseResponse<Void>> create(@RequestBody MemberCreateDto dto) {
        memberService.createMember(dto);
        return BaseResponse.success();
    }

}
