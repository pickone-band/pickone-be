package com.PickOne.domain.user.controller;


import com.PickOne.domain.user.dto.MemberStateDto.*;
import com.PickOne.domain.user.service.MemberStateService;
import com.PickOne.global.exception.BaseResponse;
import com.PickOne.global.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member-states")
@RequiredArgsConstructor
public class MemberStateController {

    private final MemberStateService memberStateService;

    @PostMapping
    public ResponseEntity<BaseResponse<MemberStateResponseDto>> create(@RequestBody MemberStateCreateDto dto) {
        // 회원 상태 생성(예: BAN)
        MemberStateResponseDto saved = memberStateService.createMemberState(dto);
        return BaseResponse.success(saved);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberStateResponseDto>>> getAll() {
        List<MemberStateResponseDto> list = memberStateService.getAllStates();
        return BaseResponse.success(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberStateResponseDto>> getOne(@PathVariable Long id) {
        MemberStateResponseDto dto = memberStateService.getMemberState(id);
        return BaseResponse.success(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberStateResponseDto>> update(@PathVariable Long id,
                                                                       @RequestParam String status,
                                                                       @RequestParam(required = false) String reason) {
        // 상태 변경
        MemberStateResponseDto updated = memberStateService.updateState(id, status, reason);
        return BaseResponse.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        memberStateService.deleteState(id);
        return BaseResponse.success(SuccessCode.DELETED);
    }
}
