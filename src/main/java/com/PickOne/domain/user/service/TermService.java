package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.TermDto;
import com.PickOne.domain.user.mapper.TermMapper;
import com.PickOne.domain.user.model.Term;
import com.PickOne.domain.user.repository.TermRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermService {

    private final TermRepository termRepository;
    private final TermMapper termMapper;

    public TermDto create(TermDto request) {
        Term term = termMapper.toEntity(request);
        return termMapper.toDto(termRepository.save(term));
    }

    public TermDto getById(Long id) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("약관을 찾을 수 없습니다. ID: " + id));
        return termMapper.toDto(term);
    }

    public List<TermDto> getAll() {
        return termRepository.findAll()
                .stream()
                .map(termMapper::toDto)
                .toList();
    }

    public TermDto update(Long id, TermDto request) {
        Term term = termRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("약관을 찾을 수 없습니다. ID: " + id));

        term.setTitle(request.getTitle());
        term.setContent(request.getContent());
        term.setVersion(request.getVersion());
        term.setIsRequired(request.getIsRequired());
        term.setIsActive(request.getIsActive());

        return termMapper.toDto(termRepository.save(term));
    }

    public void deleteById(Long id) {
        termRepository.deleteById(id);
    }
}
