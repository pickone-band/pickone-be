package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberStateDto;
import com.PickOne.domain.user.mapper.MemberStateMapper;
import com.PickOne.domain.user.model.MemberState;
import com.PickOne.domain.user.repository.MemberStateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberStateService {

    private final MemberStateRepository memberStateRepository;
    private final MemberStateMapper memberStateMapper;

    public MemberStateDto create(MemberStateDto request) {
        MemberState memberState = memberStateMapper.toEntity(request);
        return memberStateMapper.toDto(memberStateRepository.save(memberState));
    }

    public MemberStateDto getById(Long id) {
        MemberState memberState = memberStateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 상태 정보를 찾을 수 없습니다. ID: " + id));
        return memberStateMapper.toDto(memberState);
    }

    public List<MemberStateDto> getAll() {
        return memberStateRepository.findAll()
                .stream()
                .map(memberStateMapper::toDto)
                .toList();
    }

    public MemberStateDto update(Long id, MemberStateDto request) {
        MemberState memberState = memberStateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 상태 정보를 찾을 수 없습니다. ID: " + id));

        memberState.setStatus(request.getStatus());
        memberState.setBannedAt(request.getBannedAt());
        memberState.setDeletedAt(request.getDeletedAt());
        memberState.setReason(request.getReason());

        return memberStateMapper.toDto(memberStateRepository.save(memberState));
    }

    public void deleteById(Long id) {
        memberStateRepository.deleteById(id);
    }
}
