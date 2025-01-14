package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.TermDto.*;
import com.PickOne.domain.user.service.TermService;
import com.PickOne.global.exception.BaseResponse;
import com.PickOne.global.exception.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    @PostMapping
    public ResponseEntity<BaseResponse<TermResponseDto>> create(@RequestBody TermCreateDto dto) {
        TermResponseDto saved = termService.createTerm(dto);
        return BaseResponse.success(saved);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<TermResponseDto>>> getAll() {
        List<TermResponseDto> list = termService.getAllTerms();
        return BaseResponse.success(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<TermResponseDto>> getOne(@PathVariable Long id) {
        TermResponseDto dto = termService.getTerm(id);
        return BaseResponse.success(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<TermResponseDto>> update(@PathVariable Long id,
                                                                @RequestBody TermUpdateDto dto) {
        TermResponseDto updated = termService.updateTerm(id, dto);
        return BaseResponse.success(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
        termService.deleteTerm(id);
        return BaseResponse.success(SuccessCode.DELETED);
    }
}
