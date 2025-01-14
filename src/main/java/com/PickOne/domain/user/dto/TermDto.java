package com.PickOne.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

public class TermDto {

    @Getter @Setter
    public static class TermCreateDto {
        private String title;
        private String content;
        private String version;
        private Boolean isRequired;
        private Boolean isActive;
    }

    @Getter @Setter
    public static class TermUpdateDto {
        private String title;
        private String content;
        private String version;
        private Boolean isRequired;
        private Boolean isActive;
    }

    @Getter @Setter
    public static class TermResponseDto {
        private Long id;
        private String title;
        private String content;
        private String version;
        private Boolean isRequired;
        private Boolean isActive;
    }

}
