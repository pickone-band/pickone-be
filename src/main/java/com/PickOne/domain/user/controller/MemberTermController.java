package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.MemberTermDto.*;
import com.PickOne.domain.user.service.MemberTermService;
import com.PickOne.global.exception.BaseResponse;
import com.PickOne.global.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member-terms")
@RequiredArgsConstructor
public class MemberTermController {

    private final MemberTermService memberTermService;

    @PostMapping
    public ResponseEntity<BaseResponse<MemberTermResponseDto>> create(@RequestBody MemberTermCreateDto dto) {
        MemberTermResponseDto saved = memberTermService.createMemberTerm(dto);
        return BaseResponse.success(saved);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<MemberTermResponseDto>>> getAll() {
        List<MemberTermResponseDto> list = memberTermService.getAllMemberTerms();
        return BaseResponse.success(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberTermResponseDto>> getOne(@PathVariable Long id) {
        MemberTermResponseDto dto = memberTermService.getMemberTerm(id);
        return BaseResponse.success(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MemberTermResponseDto>> update(@PathVariable Long id,
                                                                      @RequestBody MemberTermUpdateDto dto) {
        MemberTermResponseDto updated = memberTermService.updateMemberTerm(id, dto);
        return BaseResponse.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        memberTermService.deleteMemberTerm(id);
        return BaseResponse.success(SuccessCode.DELETED);
    }
}
