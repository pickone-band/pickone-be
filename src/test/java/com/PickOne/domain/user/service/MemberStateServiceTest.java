package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberStateDto.MemberStateCreateDto;
import com.PickOne.domain.user.dto.MemberStateDto.MemberStateResponseDto;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.model.MemberState;
import com.PickOne.domain.user.model.MemberStatus;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.repository.MemberStateRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberStateServiceTest {

    @Autowired
    MemberStateService memberStateService;
    @Autowired
    MemberStateRepository memberStateRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        memberStateRepository.deleteAll();
        memberRepository.deleteAll();

        // 우선 테스트용 회원을 하나 저장해둡니다
        Member member = Member.builder()
                .loginId("userA")
                .password("pass")
                .username("테스트유저")
                .email("test@test.com")
                .nickname("nickA")
                .build();

        savedMember = memberRepository.save(member);
    }

    @Test
    @DisplayName("회원 상태 생성 - BANNED 성공")
    void createMemberState_banned_success() {
        // given
        MemberStateCreateDto dto = new MemberStateCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setStatus("BANNED");
        dto.setReason("규정 위반");

        // when
        MemberStateResponseDto response = memberStateService.createMemberState(dto);

        // then
        assertThat(response.getId()).isNotNull();
        MemberState found = memberStateRepository.findById(response.getId())
                .orElseThrow();
        assertThat(found.getStatus()).isEqualTo(MemberStatus.BANNED);
        assertThat(found.getBannedAt()).isNotNull();
        assertThat(found.getReason()).isEqualTo("규정 위반");
    }

    @Test
    @DisplayName("회원 상태 생성 - 존재하지 않는 회원이면 에러")
    void createMemberState_memberNotFound() {
        // given
        MemberStateCreateDto dto = new MemberStateCreateDto();
        dto.setMemberId(9999L); // 존재하지 않는 ID
        dto.setStatus("BANNED");
        dto.setReason("규정 위반");

        // when & then
        assertThatThrownBy(() -> memberStateService.createMemberState(dto))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.USER_INFO_NOT_FOUND);
    }

    @Test
    @DisplayName("회원 상태 생성 - 이미 BANNED 인 상태면 에러")
    void createMemberState_alreadyBanned() {
        // 먼저 BANNED 상태 생성
        MemberStateCreateDto dto = new MemberStateCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setStatus("BANNED");
        dto.setReason("규정 위반");
        memberStateService.createMemberState(dto);

        // 다시 BANNED로 생성 시도
        MemberStateCreateDto dto2 = new MemberStateCreateDto();
        dto2.setMemberId(savedMember.getId());
        dto2.setStatus("BANNED");
        dto2.setReason("또 규정 위반?");

        assertThatThrownBy(() -> memberStateService.createMemberState(dto2))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.ALREADY_BANNED);
    }

    @Test
    @DisplayName("회원 상태 업데이트 - BANNED -> ACTIVE")
    void updateMemberState_bannedToActive() {
        // BANNED 상태로 만들어둠
        MemberStateCreateDto dto = new MemberStateCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setStatus("BANNED");
        dto.setReason("규정 위반");
        MemberStateResponseDto response = memberStateService.createMemberState(dto);

        // when - ACTIVE 로 변경
        MemberStateResponseDto updated =
                memberStateService.updateState(response.getId(), "ACTIVE", null);

        // then
        assertThat(updated.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        MemberState found = memberStateRepository.findById(updated.getId())
                .orElseThrow();
        assertThat(found.getBannedAt()).isNull();
        assertThat(found.getReason()).isNull();
    }

    @Test
    @DisplayName("회원 상태 삭제")
    void deleteState() {
        // given
        MemberStateCreateDto dto = new MemberStateCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setStatus("BANNED");
        dto.setReason("규정 위반");
        MemberStateResponseDto response = memberStateService.createMemberState(dto);
        Long stateId = response.getId();

        // when
        memberStateService.deleteState(stateId);

        // then
        assertThat(memberStateRepository.findById(stateId)).isEmpty();
    }
}
