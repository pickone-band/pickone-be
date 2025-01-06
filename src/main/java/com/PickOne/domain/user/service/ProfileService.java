package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.ProfileDto;
import com.PickOne.domain.user.mapper.ProfileMapper;
import com.PickOne.domain.user.model.Profile;
import com.PickOne.domain.user.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileDto create(ProfileDto request) {
        Profile profile = profileMapper.toEntity(request);
        return profileMapper.toDto(profileRepository.save(profile));
    }

    public ProfileDto getById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("프로필을 찾을 수 없습니다. ID: " + id));
        return profileMapper.toDto(profile);
    }

    public List<ProfileDto> getAll() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::toDto)
                .toList();
    }

    public ProfileDto update(Long id, ProfileDto request) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("프로필을 찾을 수 없습니다. ID: " + id));

        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setBirthDate(request.getBirthDate());
        profile.setProfilePicUrl(request.getProfilePicUrl());

        return profileMapper.toDto(profileRepository.save(profile));
    }

    public void deleteById(Long id) {
        profileRepository.deleteById(id);
    }
}
