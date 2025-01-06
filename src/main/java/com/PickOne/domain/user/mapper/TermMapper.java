package com.PickOne.domain.user.mapper;

import com.PickOne.domain.user.dto.TermDto;
import com.PickOne.domain.user.model.Term;
import org.springframework.stereotype.Component;

@Component
public class TermMapper {

    public Term toEntity(TermDto dto) {
        Term term = new Term();
        term.setId(dto.getId());
        term.setTitle(dto.getTitle());
        term.setContent(dto.getContent());
        term.setVersion(dto.getVersion());
        term.setIsRequired(dto.getIsRequired());
        term.setIsActive(dto.getIsActive());
        return term;
    }

    public TermDto toDto(Term entity) {
        TermDto dto = new TermDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setVersion(entity.getVersion());
        dto.setIsRequired(entity.getIsRequired());
        dto.setIsActive(entity.getIsActive());
        return dto;
    }
}
