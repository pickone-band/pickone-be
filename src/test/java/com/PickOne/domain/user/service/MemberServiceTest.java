package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.MemberDto.*;
import com.PickOne.domain.user.model.Member;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.global.exception.BusinessException;
import com.PickOne.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearDatabase() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 가입 - 중복 LoginId")
    void createMember_duplicateLoginId() {
        // given
        MemberCreateDto dto1 = new MemberCreateDto();
        dto1.setLoginId("testUser");
        dto1.setPassword("1111");
        dto1.setUsername("유저A");
        dto1.setEmail("a@test.com");
        dto1.setNickname("nickA");
        memberService.createMember(dto1);

        // when
        MemberCreateDto dto2 = new MemberCreateDto();
        dto2.setLoginId("testUser"); // 중복
        dto2.setPassword("2222");
        dto2.setUsername("유저B");
        dto2.setEmail("b@test.com");
        dto2.setNickname("nickB");

        // then
        assertThatThrownBy(() -> memberService.createMember(dto2))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode") // 💡 `errorCode` 필드 직접 추출
                .isEqualTo(ErrorCode.DUPLICATE_LOGIN_ID);
    }

    @Test
    @DisplayName("회원 가입 - 성공")
    void createMember_success() {
        // given
        MemberCreateDto dto = new MemberCreateDto();
        dto.setLoginId("newUser");
        dto.setPassword("pass");
        dto.setUsername("새유저");
        dto.setEmail("new@test.com");
        dto.setNickname("newNick");

        // when
        MemberResponseDto response = memberService.createMember(dto);

        // then
        assertThat(response.getId()).isNotNull();
        Member found = memberRepository.findById(response.getId()).orElseThrow();
        assertThat(found.getLoginId()).isEqualTo("newUser");
        // 비밀번호 암호화 검사
        assertThat(passwordEncoder.matches("pass", found.getPassword())).isTrue();
    }
}
