package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberTermDto.MemberTermCreateDto;
import com.PickOne.domain.user.dto.MemberTermDto.MemberTermResponseDto;
import com.PickOne.domain.user.dto.MemberTermDto.MemberTermUpdateDto;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.model.MemberTerm;
import com.PickOne.domain.user.model.Term;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.repository.MemberTermRepository;
import com.PickOne.domain.user.repository.TermRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTermServiceTest {

    @Autowired
    MemberTermService memberTermService;
    @Autowired
    MemberTermRepository memberTermRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TermRepository termRepository;
    @Autowired
    ModelMapper modelMapper;

    private Member savedMember;
    private Term savedTerm;

    @BeforeEach
    void setUp() {
        memberTermRepository.deleteAll();
        termRepository.deleteAll();
        memberRepository.deleteAll();

        // 테스트용 회원
        Member member = Member.builder()
                .loginId("userA")
                .password("pass")
                .username("회원A")
                .email("userA@test.com")
                .nickname("nickA")
                .build();
        savedMember = memberRepository.save(member);

        // 활성화된 테스트용 약관
        Term term = Term.builder()
                .title("이용약관")
                .content("내용")
                .version("v1")
                .isRequired(true)
                .isActive(true)
                .build();
        savedTerm = termRepository.save(term);
    }

    @Test
    @DisplayName("회원 약관 동의 - 성공")
    void createMemberTerm_success() {
        // given
        MemberTermCreateDto dto = new MemberTermCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setTermId(savedTerm.getId());
        dto.setIsAgreed(true);

        // when
        MemberTermResponseDto response = memberTermService.createMemberTerm(dto);

        // then
        assertThat(response.getId()).isNotNull();
        MemberTerm found = memberTermRepository.findById(response.getId())
                .orElseThrow();
        assertThat(found.getIsAgreed()).isTrue();
        assertThat(found.getMember().getId()).isEqualTo(savedMember.getId());
        assertThat(found.getTerm().getId()).isEqualTo(savedTerm.getId());
    }

    @Test
    @DisplayName("회원 약관 동의 - 이미 동의한 경우 에러")
    void createMemberTerm_alreadyAgreed() {
        // 우선 동의
        MemberTermCreateDto dto = new MemberTermCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setTermId(savedTerm.getId());
        dto.setIsAgreed(true);
        memberTermService.createMemberTerm(dto);

        // 중복 동의 시도
        MemberTermCreateDto dto2 = new MemberTermCreateDto();
        dto2.setMemberId(savedMember.getId());
        dto2.setTermId(savedTerm.getId());
        dto2.setIsAgreed(true);

        assertThatThrownBy(() -> memberTermService.createMemberTerm(dto2))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.ALREADY_AGREED);
    }

    @Test
    @DisplayName("회원 약관 동의 - 약관이 비활성화 상태이면 에러")
    void createMemberTerm_inactiveTerm() {
        // 비활성화 처리
        savedTerm.setIsActive(false);
        termRepository.save(savedTerm);

        // when
        MemberTermCreateDto dto = new MemberTermCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setTermId(savedTerm.getId());
        dto.setIsAgreed(true);

        // then
        assertThatThrownBy(() -> memberTermService.createMemberTerm(dto))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.INACTIVE_TERM);
    }

    @Test
    @DisplayName("회원 약관 동의 - 존재하지 않는 회원 / 약관")
    void createMemberTerm_notFound() {
        // given
        MemberTermCreateDto dto = new MemberTermCreateDto();
        dto.setMemberId(9999L);
        dto.setTermId(8888L);
        dto.setIsAgreed(true);

        // when & then
        assertThatThrownBy(() -> memberTermService.createMemberTerm(dto))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isIn(ErrorCode.USER_INFO_NOT_FOUND, ErrorCode.TERM_NOT_FOUND);
    }

    @Test
    @DisplayName("회원 약관 동의 업데이트")
    void updateMemberTerm() {
        // given
        MemberTermCreateDto createDto = new MemberTermCreateDto();
        createDto.setMemberId(savedMember.getId());
        createDto.setTermId(savedTerm.getId());
        createDto.setIsAgreed(true);
        MemberTermResponseDto created = memberTermService.createMemberTerm(createDto);

        MemberTermUpdateDto updateDto = new MemberTermUpdateDto();
        updateDto.setIsAgreed(false);

        // when
        MemberTermResponseDto updated = memberTermService.updateMemberTerm(created.getId(), updateDto);

        // then
        assertThat(updated.getIsAgreed()).isFalse();
    }

    @Test
    @DisplayName("회원 약관 동의 삭제")
    void deleteMemberTerm() {
        // given
        MemberTermCreateDto createDto = new MemberTermCreateDto();
        createDto.setMemberId(savedMember.getId());
        createDto.setTermId(savedTerm.getId());
        createDto.setIsAgreed(true);
        MemberTermResponseDto created = memberTermService.createMemberTerm(createDto);

        // when
        memberTermService.deleteMemberTerm(created.getId());

        // then
        assertThat(memberTermRepository.findById(created.getId())).isEmpty();
    }
}
