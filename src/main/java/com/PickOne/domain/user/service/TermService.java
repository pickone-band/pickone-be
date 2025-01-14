package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.TermDto.*;
import com.PickOne.domain.user.model.Term;
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
public class TermService {

    private final TermRepository termRepository;
    private final ModelMapper modelMapper;

    public TermResponseDto createTerm(TermCreateDto dto) {
        // 버전 중복
        if (termRepository.existsByVersion(dto.getVersion())) {
            throw new BusinessException(ErrorCode.DUPLICATE_TERM_VERSION);
        }
        Term term = modelMapper.map(dto, Term.class);
        Term saved = termRepository.save(term);
        return modelMapper.map(saved, TermResponseDto.class);
    }

    @Transactional(readOnly = true)
    public TermResponseDto getTerm(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TERM_NOT_FOUND));
        return modelMapper.map(term, TermResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<TermResponseDto> getAllTerms() {
        return termRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TermResponseDto.class))
                .collect(Collectors.toList());
    }

    public TermResponseDto updateTerm(Long id, TermUpdateDto dto) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TERM_NOT_FOUND));

        // 버전 변경 시 중복
        if (!dto.getVersion().equals(term.getVersion())
                && termRepository.existsByVersion(dto.getVersion())) {
            throw new BusinessException(ErrorCode.DUPLICATE_TERM_VERSION);
        }

        term.setTitle(dto.getTitle());
        term.setContent(dto.getContent());
        term.setVersion(dto.getVersion());
        term.setIsRequired(dto.getIsRequired());
        term.setIsActive(dto.getIsActive());

        return modelMapper.map(term, TermResponseDto.class);
    }

    public void deleteTerm(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TERM_NOT_FOUND));
        termRepository.delete(term);
    }
}
