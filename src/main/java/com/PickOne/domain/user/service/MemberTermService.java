package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberTermDto.*;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.model.MemberTerm;
import com.PickOne.domain.user.model.Term;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.repository.MemberTermRepository;
import com.PickOne.domain.user.repository.TermRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberTermService {

    private final MemberTermRepository memberTermRepository;
    private final MemberRepository memberRepository;
    private final TermRepository termRepository;
    private final ModelMapper modelMapper;

    public MemberTermResponseDto createMemberTerm(MemberTermCreateDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_INFO_NOT_FOUND));
        Term term = termRepository.findById(dto.getTermId())
                .orElseThrow(() -> new BusinessException(ErrorCode.TERM_NOT_FOUND));

        // 중복 동의 체크
        if (memberTermRepository.existsByMemberAndTerm(member, term)) {
            throw new BusinessException(ErrorCode.ALREADY_AGREED);
        }
        // 활성화 안 된 약관이면 -> 에러
        if (!Boolean.TRUE.equals(term.getIsActive())) {
            throw new BusinessException(ErrorCode.INACTIVE_TERM);
        }

        MemberTerm mt = modelMapper.map(dto, MemberTerm.class);
        mt.setMember(member);
        mt.setTerm(term);

        MemberTerm saved = memberTermRepository.save(mt);
        return modelMapper.map(saved, MemberTermResponseDto.class);
    }

    @Transactional(readOnly = true)
    public MemberTermResponseDto getMemberTerm(Long id) {
        MemberTerm mt = memberTermRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_TERM_NOT_FOUND));
        return modelMapper.map(mt, MemberTermResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<MemberTermResponseDto> getAllMemberTerms() {
        return memberTermRepository.findAll().stream()
                .map(mt -> modelMapper.map(mt, MemberTermResponseDto.class))
                .collect(Collectors.toList());
    }

    public MemberTermResponseDto updateMemberTerm(Long id, MemberTermUpdateDto dto) {
        MemberTerm mt = memberTermRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_TERM_NOT_FOUND));

        mt.setIsAgreed(dto.getIsAgreed());
        return modelMapper.map(mt, MemberTermResponseDto.class);
    }

    public void deleteMemberTerm(Long id) {
        MemberTerm mt = memberTermRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_TERM_NOT_FOUND));
        memberTermRepository.delete(mt);
    }
}
