package com.PickOne.domain.user.mapper;

import com.PickOne.domain.user.dto.MemberTermDto;
import com.PickOne.domain.user.model.MemberTerm;
import org.springframework.stereotype.Component;

@Component
public class MemberTermMapper {

    public MemberTerm toEntity(MemberTermDto dto) {
        MemberTerm memberTerms = new MemberTerm();
        memberTerms.setId(dto.getId());
        memberTerms.setIsAgreed(dto.getIsAgreed());
        return memberTerms;
    }

    public MemberTermDto toDto(MemberTerm entity) {
        MemberTermDto dto = new MemberTermDto();
        dto.setId(entity.getId());
        dto.setIsAgreed(entity.getIsAgreed());
        return dto;
    }
}
