package com.PickOne.domain.user.dto;

import com.PickOne.domain.user.model.SocialProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialAccountDto {
    private Long id;
    private SocialProvider provider;
    private String providerUserId;
}
