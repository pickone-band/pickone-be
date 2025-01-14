package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.ProfileDto.ProfileCreateDto;
import com.PickOne.domain.user.dto.ProfileDto.ProfileResponseDto;
import com.PickOne.domain.user.dto.ProfileDto.ProfileUpdateDto;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.model.Profile;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.repository.ProfileRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfileServiceTest {

    @Autowired
    ProfileService profileService;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        profileRepository.deleteAll();
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
    @DisplayName("프로필 생성 - 성공")
    void createProfile_success() {
        // given
        ProfileCreateDto dto = new ProfileCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setPhoneNumber("010-1111-2222");
        dto.setBirthDate(LocalDate.of(2000, 1, 1));
        dto.setProfilePicUrl("http://example.com/img.jpg");

        // when
        ProfileResponseDto response = profileService.createProfile(dto);

        // then
        assertThat(response.getId()).isNotNull();
        Profile found = profileRepository.findById(response.getId()).orElseThrow();
        assertThat(found.getPhoneNumber()).isEqualTo("010-1111-2222");
    }

    @Test
    @DisplayName("프로필 생성 - 중복 전화번호 에러")
    void createProfile_duplicatePhoneNumber() {
        // 먼저 하나 생성
        ProfileCreateDto dto1 = new ProfileCreateDto();
        dto1.setMemberId(savedMember.getId());
        dto1.setPhoneNumber("010-9999-9999");
        dto1.setBirthDate(LocalDate.of(1990, 1, 1));
        profileService.createProfile(dto1);

        // 같은 번호로 다시 생성
        ProfileCreateDto dto2 = new ProfileCreateDto();
        dto2.setMemberId(savedMember.getId());
        dto2.setPhoneNumber("010-9999-9999");
        dto2.setBirthDate(LocalDate.of(1991, 2, 2));

        assertThatThrownBy(() -> profileService.createProfile(dto2))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_PHONE_NUMBER);
    }

    @Test
    @DisplayName("프로필 업데이트 - 전화번호 변경 시 중복 체크")
    void updateProfile_duplicatePhoneNumber() {
        // 프로필 A
        ProfileCreateDto dtoA = new ProfileCreateDto();
        dtoA.setMemberId(savedMember.getId());
        dtoA.setPhoneNumber("010-0000-0000");
        dtoA.setBirthDate(LocalDate.of(2000, 1, 1));
        ProfileResponseDto createdA = profileService.createProfile(dtoA);

        // 프로필 B
        Member memberB = memberRepository.save(Member.builder()
                .loginId("userB")
                .password("pass")
                .username("회원B")
                .email("userB@test.com")
                .nickname("nickB")
                .build());
        ProfileCreateDto dtoB = new ProfileCreateDto();
        dtoB.setMemberId(memberB.getId());
        dtoB.setPhoneNumber("010-1111-1111");
        dtoB.setBirthDate(LocalDate.of(1995, 5, 5));
        ProfileResponseDto createdB = profileService.createProfile(dtoB);

        // when: B의 번호로 A를 업데이트하려고 하면?
        ProfileUpdateDto updateDto = new ProfileUpdateDto();
        updateDto.setPhoneNumber("010-1111-1111"); // 이미 B의 번호
        updateDto.setProfilePicUrl("http://example.com/new.jpg");
        updateDto.setBirthDate(LocalDate.of(2001, 2, 2));

        // then
        assertThatThrownBy(() -> profileService.updateProfile(createdA.getId(), updateDto))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_PHONE_NUMBER);
    }

    @Test
    @DisplayName("프로필 삭제")
    void deleteProfile() {
        // given
        ProfileCreateDto dto = new ProfileCreateDto();
        dto.setMemberId(savedMember.getId());
        dto.setPhoneNumber("010-2222-2222");
        dto.setBirthDate(LocalDate.of(1995, 12, 12));
        ProfileResponseDto created = profileService.createProfile(dto);

        // when
        profileService.deleteProfile(created.getId());

        // then
        assertThat(profileRepository.findById(created.getId())).isEmpty();
    }
}
