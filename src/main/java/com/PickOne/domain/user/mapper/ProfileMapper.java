package com.PickOne.domain.user.mapper;

import com.PickOne.domain.user.dto.ProfileDto;
import com.PickOne.domain.user.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public Profile toEntity(ProfileDto dto) {
        Profile profile = new Profile();
        profile.setId(dto.getId());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setBirthDate(dto.getBirthDate());
        profile.setProfilePicUrl(dto.getProfilePicUrl());
        return profile;
    }

    public ProfileDto toDto(Profile entity) {
        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setBirthDate(entity.getBirthDate());
        dto.setProfilePicUrl(entity.getProfilePicUrl());
        return dto;
    }
}
