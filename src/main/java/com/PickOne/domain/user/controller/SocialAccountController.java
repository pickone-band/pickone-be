package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.SocialAccountDto;
import com.PickOne.domain.user.service.SocialAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social-accounts")
public class SocialAccountController {

    private final SocialAccountService socialAccountService;

    @PostMapping
    public ResponseEntity<SocialAccountDto> createSocialAccount(@Valid @RequestBody SocialAccountDto request) {
        return ResponseEntity.ok(socialAccountService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialAccountDto> getSocialAccount(@PathVariable Long id) {
        return ResponseEntity.ok(socialAccountService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SocialAccountDto>> getAllSocialAccounts() {
        return ResponseEntity.ok(socialAccountService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialAccountDto> updateSocialAccount(@PathVariable Long id, @Valid @RequestBody SocialAccountDto request) {
        return ResponseEntity.ok(socialAccountService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSocialAccount(@PathVariable Long id) {
        socialAccountService.deleteById(id);
        return ResponseEntity.ok("소셜 계정 삭제 완료. ID: " + id);
    }
}
