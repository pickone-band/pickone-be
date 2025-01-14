package com.PickOne.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberTermDto {

    @Getter
    @Setter
    public static class MemberTermCreateDto {
        private Long memberId;
        private Long termId;
        private Boolean isAgreed;
    }

    @Getter
    @Setter
    public static class MemberTermUpdateDto {
        private Boolean isAgreed;
    }

    @Getter
    @Setter
    public class MemberTermResponseDto {
        private Long id;
        private Long memberId;
        private Long termId;
        private Boolean isAgreed;
    }
}
