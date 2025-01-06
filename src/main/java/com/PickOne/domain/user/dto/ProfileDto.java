package com.PickOne.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Long id;
    private String phoneNumber;
    private String birthDate;
    private String profilePicUrl;
}
