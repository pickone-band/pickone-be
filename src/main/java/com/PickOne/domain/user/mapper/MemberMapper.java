package com.PickOne.domain.user.mapper;

import com.PickOne.domain.user.dto.MemberDto;
import com.PickOne.domain.user.model.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberDto dto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setLoginId(dto.getLoginId());
        member.setPassword(passwordEncoder.encode(dto.getPassword())); // 비밀번호 암호화
        member.setUsername(dto.getUsername());
        member.setEmail(dto.getEmail());
        member.setNickname(dto.getNickname());
        return member;
    }

    public MemberDto toDto(Member entity) {
        MemberDto dto = new MemberDto();
        dto.setId(entity.getId());
        dto.setLoginId(entity.getLoginId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setNickname(entity.getNickname());
        return dto;
    }
}
