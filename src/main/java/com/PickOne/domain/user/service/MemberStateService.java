package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberStateDto.*;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.model.MemberState;
import com.PickOne.domain.user.model.MemberStatus;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.repository.MemberStateRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberStateService {

    private final MemberRepository memberRepository;
    private final MemberStateRepository memberStateRepository;
    private final ModelMapper modelMapper;

    /** 특정 회원을 상태 변경 (ex: BAN) */
    public MemberStateResponseDto createMemberState(MemberStateCreateDto dto) {
        // memberId 필수
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_INFO_NOT_FOUND));

        MemberState state = MemberState.builder()
                .member(member)
                .build();

        MemberStatus newStatus = MemberStatus.valueOf(dto.getStatus());
        if (newStatus == MemberStatus.BANNED) {
            // 이미 BANNED인 경우
            if (member.getMemberState() != null
                    && member.getMemberState().getStatus() == MemberStatus.BANNED) {
                throw new BusinessException(ErrorCode.ALREADY_BANNED);
            }
            state.setBannedAt(LocalDateTime.now());
            state.setReason(dto.getReason());
        }
        else if (newStatus == MemberStatus.ACTIVE) {
            if (member.getMemberState() != null
                    && member.getMemberState().getStatus() == MemberStatus.ACTIVE) {
                throw new BusinessException(ErrorCode.ALREADY_ACTIVE);
            }
        }
        state.setStatus(newStatus);

        MemberState saved = memberStateRepository.save(state);
        return modelMapper.map(saved, MemberStateResponseDto.class);
    }

    @Transactional(readOnly = true)
    public MemberStateResponseDto getMemberState(Long id) {
        MemberState ms = memberStateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        return modelMapper.map(ms, MemberStateResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<MemberStateResponseDto> getAllStates() {
        return memberStateRepository.findAll().stream()
                .map(ms -> modelMapper.map(ms, MemberStateResponseDto.class))
                .collect(Collectors.toList());
    }

    /** 상태 수정 (ex: 정지 -> 해제) */
    public MemberStateResponseDto updateState(Long id, String newStatusStr, String reason) {
        MemberState ms = memberStateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        MemberStatus newStatus = MemberStatus.valueOf(newStatusStr);
        if (newStatus == MemberStatus.BANNED) {
            if (ms.getStatus() == MemberStatus.BANNED) {
                throw new BusinessException(ErrorCode.ALREADY_BANNED);
            }
            ms.setBannedAt(LocalDateTime.now());
            ms.setReason(reason);
        } else if (newStatus == MemberStatus.ACTIVE) {
            if (ms.getStatus() == MemberStatus.ACTIVE) {
                throw new BusinessException(ErrorCode.ALREADY_ACTIVE);
            }
            ms.setBannedAt(null);
            ms.setReason(null);
        }
        ms.setStatus(newStatus);

        return modelMapper.map(ms, MemberStateResponseDto.class);
    }

    public void deleteState(Long id) {
        MemberState ms = memberStateRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
        memberStateRepository.delete(ms);
    }
}
