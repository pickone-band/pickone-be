package com.PickOne.domain.user.dto;

import com.PickOne.domain.user.model.MemberStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberStateDto {
    private Long id;
    private MemberStatus status;
    private LocalDateTime bannedAt;
    private LocalDateTime deletedAt;
    private String reason;
}
