package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.MemberStateDto;
import com.PickOne.domain.user.service.MemberStateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member-states")
public class MemberStateController {

    private final MemberStateService memberStateService;

    @PostMapping
    public ResponseEntity<MemberStateDto> createMemberState(@Valid @RequestBody MemberStateDto request) {
        return ResponseEntity.ok(memberStateService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberStateDto> getMemberState(@PathVariable Long id) {
        return ResponseEntity.ok(memberStateService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MemberStateDto>> getAllMemberStates() {
        return ResponseEntity.ok(memberStateService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberStateDto> updateMemberState(@PathVariable Long id, @Valid @RequestBody MemberStateDto request) {
        return ResponseEntity.ok(memberStateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMemberState(@PathVariable Long id) {
        memberStateService.deleteById(id);
        return ResponseEntity.ok("회원 상태 삭제 완료. ID: " + id);
    }
}
