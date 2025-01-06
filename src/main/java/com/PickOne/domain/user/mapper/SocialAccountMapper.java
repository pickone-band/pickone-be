package com.PickOne.domain.user.mapper;

import com.PickOne.domain.user.dto.SocialAccountDto;
import com.PickOne.domain.user.model.SocialAccount;
import org.springframework.stereotype.Component;

@Component
public class SocialAccountMapper {

    public SocialAccount toEntity(SocialAccountDto dto) {
        SocialAccount socialAccount = new SocialAccount();
        socialAccount.setId(dto.getId());
        socialAccount.setProvider(dto.getProvider());
        socialAccount.setProviderUserId(dto.getProviderUserId());
        return socialAccount;
    }

    public SocialAccountDto toDto(SocialAccount entity) {
        SocialAccountDto dto = new SocialAccountDto();
        dto.setId(entity.getId());
        dto.setProvider(entity.getProvider());
        dto.setProviderUserId(entity.getProviderUserId());
        return dto;
    }
}
