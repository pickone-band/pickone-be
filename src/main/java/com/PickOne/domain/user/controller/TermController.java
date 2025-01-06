package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.TermDto;
import com.PickOne.domain.user.service.TermService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/terms")
public class TermController {

    private final TermService termService;

    @PostMapping
    public ResponseEntity<TermDto> createTerm(@Valid @RequestBody TermDto request) {
        return ResponseEntity.ok(termService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TermDto> getTerm(@PathVariable Long id) {
        return ResponseEntity.ok(termService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TermDto>> getAllTerms() {
        return ResponseEntity.ok(termService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TermDto> updateTerm(@PathVariable Long id, @Valid @RequestBody TermDto request) {
        return ResponseEntity.ok(termService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTerm(@PathVariable Long id) {
        termService.deleteById(id);
        return ResponseEntity.ok("약관 삭제 완료. ID: " + id);
    }
}
