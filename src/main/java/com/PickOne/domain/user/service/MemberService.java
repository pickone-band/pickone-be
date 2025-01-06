package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberDto;
import com.PickOne.domain.user.mapper.MemberMapper;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberDto create(MemberDto request) {
        Member member = memberMapper.toEntity(request, passwordEncoder);
        return memberMapper.toDto(memberRepository.save(member));
    }

    public MemberDto getById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다. ID: " + id));
        return memberMapper.toDto(member);
    }

    public List<MemberDto> getAll() {
        return memberRepository.findAll()
                .stream()
                .map(memberMapper::toDto)
                .toList();
    }

    public MemberDto update(Long id, MemberDto request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다. ID: " + id));

        member.setUsername(request.getUsername());
        member.setEmail(request.getEmail());
        member.setNickname(request.getNickname());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(request.getPassword())); // 비밀번호 업데이트 시 암호화
        }

        return memberMapper.toDto(memberRepository.save(member));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
