package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.SocialAccountDto.SocialAccountCreateDto;
import com.PickOne.domain.user.dto.SocialAccountDto.SocialAccountResponseDto;
import com.PickOne.domain.user.dto.SocialAccountDto.SocialAccountUpdateDto;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.model.SocialAccount;
import com.PickOne.domain.user.model.SocialProvider;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.repository.SocialAccountRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.PickOne.domain.user.model.SocialProvider.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class SocialAccountServiceTest {

    @Autowired
    SocialAccountService socialAccountService;
    @Autowired
    SocialAccountRepository socialAccountRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        socialAccountRepository.deleteAll();
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
    }

    @Test
    @DisplayName("소셜 계정 생성 - 성공")
    void createSocialAccount_success() {
        // given
        SocialAccountCreateDto dto = new SocialAccountCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setProvider(KAKAO);
        dto.setProviderUserId("kakao_12345");

        // when
        SocialAccountResponseDto response = socialAccountService.createSocialAccount(dto);

        // then
        assertThat(response.getId()).isNotNull();
        SocialAccount found = socialAccountRepository.findById(response.getId()).orElseThrow();
        assertThat(found.getProvider()).isEqualTo(KAKAO);
        assertThat(found.getProviderUserId()).isEqualTo("kakao_12345");
    }

    @Test
    @DisplayName("소셜 계정 생성 - 이미 같은 provider 가 존재하면 에러")
    void createSocialAccount_duplicateProvider() {
        // 이미 하나 생성
        SocialAccountCreateDto dto1 = new SocialAccountCreateDto();
        dto1.setMemberId(savedMember.getId());
        dto1.setProvider(KAKAO);
        dto1.setProviderUserId("kakao_11111");
        socialAccountService.createSocialAccount(dto1);

        // 같은 provider 로 다시 생성
        SocialAccountCreateDto dto2 = new SocialAccountCreateDto();
        dto2.setMemberId(savedMember.getId());
        dto2.setProvider(KAKAO);
        dto2.setProviderUserId("kakao_22222");

        assertThatThrownBy(() -> socialAccountService.createSocialAccount(dto2))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_SOCIAL_ACCOUNT);
    }

    @Test
    @DisplayName("소셜 계정 업데이트 - provider 변경 시 중복 에러")
    void updateSocialAccount_duplicateProvider() {
        // 소셜 계정 A: KAKAO
        SocialAccountCreateDto dtoA = new SocialAccountCreateDto();
        dtoA.setMemberId(savedMember.getId());
        dtoA.setProvider(KAKAO);
        dtoA.setProviderUserId("kakao_111");
        SocialAccountResponseDto createdA = socialAccountService.createSocialAccount(dtoA);

        // 소셜 계정 B: NAVER
        SocialAccountCreateDto dtoB = new SocialAccountCreateDto();
        dtoB.setMemberId(savedMember.getId());
        dtoB.setProvider(SocialProvider.NAVER);
        dtoB.setProviderUserId("naver_222");
        SocialAccountResponseDto createdB = socialAccountService.createSocialAccount(dtoB);

        // when: A를 NAVER로 바꾸려고 하면 중복 에러
        SocialAccountUpdateDto updateDto = new SocialAccountUpdateDto();
        updateDto.setProvider(SocialProvider.NAVER);
        updateDto.setProviderUserId("naver_333");

        assertThatThrownBy(() -> socialAccountService.updateSocialAccount(createdA.getId(), updateDto))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_SOCIAL_ACCOUNT);
    }

    @Test
    @DisplayName("소셜 계정 삭제")
    void deleteSocialAccount() {
        // given
        SocialAccountCreateDto dto = new SocialAccountCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setProvider(KAKAO);
        dto.setProviderUserId("kakao_9999");
        SocialAccountResponseDto created = socialAccountService.createSocialAccount(dto);

        // when
        socialAccountService.deleteSocialAccount(created.getId());

        // then
        assertThat(socialAccountRepository.findById(created.getId())).isEmpty();
    }
}
