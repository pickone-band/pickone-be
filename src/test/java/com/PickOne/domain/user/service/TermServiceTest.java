package com.PickOne.domain.user.service;

import com.PickOne.domain.user.dto.TermDto.TermCreateDto;
import com.PickOne.domain.user.dto.TermDto.TermResponseDto;
import com.PickOne.domain.user.dto.TermDto.TermUpdateDto;
import com.PickOne.domain.user.model.Term;
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
class TermServiceTest {

    @Autowired
    TermService termService;
    @Autowired
    TermRepository termRepository;
    @Autowired
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        termRepository.deleteAll();
    }

    @Test
    @DisplayName("약관 생성 - 성공")
    void createTerm_success() {
        // given
        TermCreateDto dto = new TermCreateDto();
        dto.setTitle("이용약관");
        dto.setContent("약관 내용");
        dto.setVersion("v1");
        dto.setIsRequired(true);
        dto.setIsActive(true);

        // when
        TermResponseDto response = termService.createTerm(dto);

        // then
        assertThat(response.getId()).isNotNull();
        Term found = termRepository.findById(response.getId()).orElseThrow();
        assertThat(found.getTitle()).isEqualTo("이용약관");
        assertThat(found.getVersion()).isEqualTo("v1");
    }

    @Test
    @DisplayName("약관 생성 - 버전 중복 시 에러")
    void createTerm_duplicateVersion() {
        // given
        TermCreateDto dto1 = new TermCreateDto();
        dto1.setTitle("약관1");
        dto1.setContent("내용1");
        dto1.setVersion("v1");
        dto1.setIsRequired(false);
        dto1.setIsActive(true);
        termService.createTerm(dto1);

        // when
        TermCreateDto dto2 = new TermCreateDto();
        dto2.setTitle("약관2");
        dto2.setContent("내용2");
        dto2.setVersion("v1"); // 동일 버전
        dto2.setIsRequired(true);
        dto2.setIsActive(false);

        // then
        assertThatThrownBy(() -> termService.createTerm(dto2))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_TERM_VERSION);
    }

    @Test
    @DisplayName("약관 업데이트 - 버전 중복 시 에러")
    void updateTerm_duplicateVersion() {
        // 주어진 약관 생성
        TermCreateDto dto1 = new TermCreateDto();
        dto1.setTitle("약관1");
        dto1.setContent("내용1");
        dto1.setVersion("v1");
        dto1.setIsRequired(false);
        dto1.setIsActive(true);
        TermResponseDto created1 = termService.createTerm(dto1);

        // 또 다른 약관 만들어둠
        TermCreateDto dto2 = new TermCreateDto();
        dto2.setTitle("약관2");
        dto2.setContent("내용2");
        dto2.setVersion("v2");
        dto2.setIsRequired(true);
        dto2.setIsActive(true);
        termService.createTerm(dto2);

        // when: 첫 번째 약관 v1 -> v2 로 바꾸려고 하면?
        TermUpdateDto updateDto = new TermUpdateDto();
        updateDto.setTitle("약관1 수정");
        updateDto.setContent("내용1 수정");
        updateDto.setVersion("v2"); // 이미 존재
        updateDto.setIsRequired(true);
        updateDto.setIsActive(true);

        // then
        assertThatThrownBy(() -> termService.updateTerm(created1.getId(), updateDto))
                .isInstanceOf(BusinessException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.DUPLICATE_TERM_VERSION);
    }

    @Test
    @DisplayName("약관 삭제")
    void deleteTerm() {
        // given
        TermCreateDto dto = new TermCreateDto();
        dto.setTitle("약관1");
        dto.setContent("내용1");
        dto.setVersion("v1");
        dto.setIsRequired(true);
        dto.setIsActive(false);
        TermResponseDto created = termService.createTerm(dto);

        // when
        termService.deleteTerm(created.getId());

        // then
        assertThat(termRepository.findById(created.getId())).isEmpty();
    }
}
