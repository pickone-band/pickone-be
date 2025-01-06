package com.PickOne.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermDto {
    private Long id;
    private String title;
    private String content;
    private String version;
    private Boolean isRequired;
    private Boolean isActive;
}
