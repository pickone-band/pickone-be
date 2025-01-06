package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.SocialAccountDto;
import com.PickOne.domain.user.mapper.SocialAccountMapper;
import com.PickOne.domain.user.model.SocialAccount;
import com.PickOne.domain.user.repository.SocialAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialAccountService {

    private final SocialAccountRepository socialAccountRepository;
    private final SocialAccountMapper socialAccountMapper;

    public SocialAccountDto create(SocialAccountDto request) {
        SocialAccount socialAccount = socialAccountMapper.toEntity(request);
        return socialAccountMapper.toDto(socialAccountRepository.save(socialAccount));
    }

    public SocialAccountDto getById(Long id) {
        SocialAccount socialAccount = socialAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("소셜 계정을 찾을 수 없습니다. ID: " + id));
        return socialAccountMapper.toDto(socialAccount);
    }

    public List<SocialAccountDto> getAll() {
        return socialAccountRepository.findAll()
                .stream()
                .map(socialAccountMapper::toDto)
                .toList();
    }

    public SocialAccountDto update(Long id, SocialAccountDto request) {
        SocialAccount socialAccount = socialAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("소셜 계정을 찾을 수 없습니다. ID: " + id));

        socialAccount.setProvider(request.getProvider());
        socialAccount.setProviderUserId(request.getProviderUserId());

        return socialAccountMapper.toDto(socialAccountRepository.save(socialAccount));
    }

    public void deleteById(Long id) {
        socialAccountRepository.deleteById(id);
    }
}
