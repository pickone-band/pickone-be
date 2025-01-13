package com.PickOne.domain.preference.controller;

import com.PickOne.domain.preference.dto.PreferenceRegisterDto;
import com.PickOne.domain.preference.dto.UserGenreRequestDto;
import com.PickOne.domain.preference.dto.UserInstrumentRequestDto;
import com.PickOne.domain.preference.dto.response.PreferenceResponseDto;
import com.PickOne.domain.preference.service.PreferenceService;
import com.PickOne.global.exception.BaseResponse;
import com.PickOne.global.exception.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/preference")
@RequiredArgsConstructor

public class PreferenceController {
    private final PreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<BaseResponse<PreferenceResponseDto>> loadPreferenceInfo( //1명의 취향조회
            @RequestParam Long memberId) { //시큐리티 적용 후 @AuthenticationPrincipal 바꿀 예정
        PreferenceResponseDto preferenceResponseDto = preferenceService.loadPreference(memberId);
        return BaseResponse.success(preferenceResponseDto);
    }


    @PostMapping()
    public ResponseEntity<BaseResponse<Void>> savePreferenceInfo(
            @RequestBody @Valid PreferenceRegisterDto preferenceRegisterDto,
            @RequestParam Long memberId) { //시큐리티 적용 후 @AuthenticationPrincipal 바꿀 예정

        preferenceService.registerPreference(preferenceRegisterDto, memberId);

        if (preferenceRegisterDto.getUserGenres() != null) {

            UserGenreRequestDto userGenreRequestDto = UserGenreRequestDto.builder()
                    .genre(preferenceRegisterDto.getUserGenres())
                    .build();
            preferenceService.registerUserGenre(userGenreRequestDto, memberId);
        }
        if (preferenceRegisterDto.getUserInstrumentDetails() != null) {

            UserInstrumentRequestDto userInstrumentRequestDto = UserInstrumentRequestDto.builder()
                    .instrumentDetails(preferenceRegisterDto.getUserInstrumentDetails())
                    .build();
            preferenceService.registerUserInstrument(userInstrumentRequestDto, memberId);
        }

        return BaseResponse.success(SuccessCode.CREATED);
    }
}
