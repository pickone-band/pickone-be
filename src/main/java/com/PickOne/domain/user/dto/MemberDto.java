package com.PickOne.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long id;
    private String loginId;
    private String password; // 비밀번호 추가
    private String username;
    private String email;
    private String nickname;
}
