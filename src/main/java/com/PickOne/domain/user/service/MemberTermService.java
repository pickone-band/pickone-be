package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberTermDto;
import com.PickOne.domain.user.mapper.MemberTermMapper;
import com.PickOne.domain.user.model.MemberTerm;
import com.PickOne.domain.user.repository.MemberTermRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberTermService {

    private final MemberTermRepository MemberTermRepository;
    private final MemberTermMapper MemberTermMapper;

    public MemberTermDto create(MemberTermDto request) {
        MemberTerm MemberTerm = MemberTermMapper.toEntity(request);
        return MemberTermMapper.toDto(MemberTermRepository.save(MemberTerm));
    }

    public MemberTermDto getById(Long id) {
        MemberTerm MemberTerm = MemberTermRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 약관 정보를 찾을 수 없습니다. ID: " + id));
        return MemberTermMapper.toDto(MemberTerm);
    }

    public List<MemberTermDto> getAll() {
        return MemberTermRepository.findAll()
                .stream()
                .map(MemberTermMapper::toDto)
                .toList();
    }

    public MemberTermDto update(Long id, MemberTermDto request) {
        MemberTerm MemberTerm = MemberTermRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 약관 정보를 찾을 수 없습니다. ID: " + id));

        MemberTerm.setIsAgreed(request.getIsAgreed());
        return MemberTermMapper.toDto(MemberTermRepository.save(MemberTerm));
    }

    public void deleteById(Long id) {
        MemberTermRepository.deleteById(id);
    }
}
