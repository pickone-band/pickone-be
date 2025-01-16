package com.PickOne.domain.user.controller;

import com.PickOne.domain.user.dto.MemberDto.*;
import com.PickOne.domain.user.repository.MemberRepository;
import com.PickOne.domain.user.service.MemberService;
import com.PickOne.global.exception.SuccessCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void signUpTest() throws Exception {
        // given
        MemberCreateDto createDto = new MemberCreateDto();
        createDto.setLoginId("testUser");
        createDto.setPassword("testPass");
        createDto.setUsername("유저");
        createDto.setEmail("user@test.com");
        createDto.setNickname("닉네임");

        doNothing().when(memberService).createMember(any(MemberCreateDto.class));

        // when & then
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "loginId": "testUser",
                                  "password": "testPass",
                                  "username": "유저",
                                  "email": "user@test.com",
                                  "nickname": "닉네임"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.message").value(SuccessCode.CREATED.getMessage()));
    }

    @Test
    @DisplayName("전체 회원 조회 테스트")
    void getAllTest() throws Exception {
        // given
        MemberResponseDto dto1 = new MemberResponseDto();
        dto1.setId(1L);
        dto1.setLoginId("user1");
        dto1.setUsername("유저1");
        dto1.setEmail("user1@test.com");
        dto1.setNickname("닉네임1");

        MemberResponseDto dto2 = new MemberResponseDto();
        dto2.setId(2L);
        dto2.setLoginId("user2");
        dto2.setUsername("유저2");
        dto2.setEmail("user2@test.com");
        dto2.setNickname("닉네임2");

        List<MemberResponseDto> responseList = Arrays.asList(dto1, dto2);
        when(memberService.getAllMembers()).thenReturn(responseList);

        // when & then
        mockMvc.perform(get("/api/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.message").value(SuccessCode.OK.getMessage()))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[0].username").value("유저1"))
                .andExpect(jsonPath("$.result[1].username").value("유저2"));
    }

    @Test
    @DisplayName("단일 회원 조회 테스트")
    void getOneTest() throws Exception {
        // given
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(1L);
        dto.setLoginId("user1");
        dto.setUsername("유저1");
        dto.setEmail("user1@test.com");
        dto.setNickname("닉네임1");

        when(memberService.getMember(1L)).thenReturn(dto);

        // when & then
        mockMvc.perform(get("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.message").value(SuccessCode.OK.getMessage()))
                .andExpect(jsonPath("$.result.username").value("유저1"))
                .andExpect(jsonPath("$.result.email").value("user1@test.com"));
    }


}
