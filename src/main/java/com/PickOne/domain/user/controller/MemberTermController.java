package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.MemberTermDto;
import com.PickOne.domain.user.service.MemberTermService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member-terms")
public class MemberTermController {

    private final MemberTermService MemberTermService;

    @PostMapping
    public ResponseEntity<MemberTermDto> createMemberTerm(@Valid @RequestBody MemberTermDto request) {
        return ResponseEntity.ok(MemberTermService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberTermDto> getMemberTerm(@PathVariable Long id) {
        return ResponseEntity.ok(MemberTermService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberTermDto>> getAllMemberTerm() {
        return ResponseEntity.ok(MemberTermService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberTermDto> updateMemberTerm(@PathVariable Long id, @Valid @RequestBody MemberTermDto request) {
        return ResponseEntity.ok(MemberTermService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMemberTerm(@PathVariable Long id) {
        MemberTermService.deleteById(id);
        return ResponseEntity.ok("회원 약관 정보 삭제 완료. ID: " + id);
    }
}
