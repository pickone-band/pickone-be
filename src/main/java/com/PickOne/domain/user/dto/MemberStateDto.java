package com.PickOne.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MemberStateDto {
    @Getter
    @Setter
    public static class MemberStateCreateDto {
        private Long memberId; // 어떤 Member에 연결할지
        private String status; // ACTIVE, BANNED, DELETED
        private LocalDateTime bannedAt;
        private LocalDateTime deletedAt;
        private String reason;
    }

    @Getter
    @Setter
    public class MemberStateUpdateDto {
        private String status;
        private LocalDateTime bannedAt;
        private LocalDateTime deletedAt;
        private String reason;
    }

    @Getter
    @Setter
    public class MemberStateResponseDto {
        private Long id;
        private Long memberId;
        private String status;
        private LocalDateTime bannedAt;
        private LocalDateTime deletedAt;
        private String reason;
        // createdAt, updatedAt 필요하면 추가
    }
}
