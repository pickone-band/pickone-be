package com.PickOne.domain.user.mapper;

import com.PickOne.domain.user.dto.MemberStateDto;
import com.PickOne.domain.user.model.MemberState;
import org.springframework.stereotype.Component;

@Component
public class MemberStateMapper {

    public MemberState toEntity(MemberStateDto dto) {
        MemberState memberState = new MemberState();
        memberState.setId(dto.getId());
        memberState.setStatus(dto.getStatus());
        memberState.setBannedAt(dto.getBannedAt());
        memberState.setDeletedAt(dto.getDeletedAt());
        memberState.setReason(dto.getReason());
        return memberState;
    }

    public MemberStateDto toDto(MemberState entity) {
        MemberStateDto dto = new MemberStateDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setBannedAt(entity.getBannedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setReason(entity.getReason());
        return dto;
    }
}
