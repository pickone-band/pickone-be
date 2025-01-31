package com.PickOne.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 1001, "잘못된 값을 입력했습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 1002, "허용되지 않은 메서드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.BAD_REQUEST, 1003, "엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1004, "서버 오류가 발생하였습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, 1005, "잘못된 유형 값을 입력하였습니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, 1006, "액세스가 거부되었습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, 1007, "비정상적 접근입니다."),
    INVALID_DATE(HttpStatus.BAD_REQUEST, 1008, "날짜 형식을 확인해주세요."),
    EMPTY_PATH_VARIABLE(HttpStatus.BAD_REQUEST, 1008, "필수 경로 변수가 누락되었습니다. 요청 경로에 올바른 값을 입력해 주세요."),
    NOT_SUPPORTED_TYPE(HttpStatus.BAD_REQUEST, 1009, "잘못된 형식 파일입니다."),
    FILE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 1010, "파일 삭제 중 오류가 발생했습니다."),

    // 회원 관련
    USER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "해당 회원의 정보를 찾을 수 없습니다."),
    AUTHORIZATION_DENIED(HttpStatus.FORBIDDEN, 2504, "권한이 없습니다."),

    PREFERENCE_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "해당 회원의 취향정보를 찾을 수 없습니다."),




    // (새로 추가) 회원 중복
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, 2101, "이미 사용 중인 로그인 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, 2102, "이미 사용 중인 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, 2103, "이미 사용 중인 닉네임입니다."),

    // MemberState 관련 (예: 이미 BAN 상태인데 BAN 시도, 등)
    ALREADY_BANNED(HttpStatus.BAD_REQUEST, 2201, "이미 정지된 회원입니다."),
    ALREADY_ACTIVE(HttpStatus.BAD_REQUEST, 2202, "이미 활성 상태의 회원입니다."),

    // Profile 관련
    DUPLICATE_PHONE_NUMBER(HttpStatus.BAD_REQUEST, 2301, "이미 사용 중인 전화번호입니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, 2302, "해당 프로필 정보를 찾을 수 없습니다."),

    // SocialAccount 관련
    DUPLICATE_SOCIAL_ACCOUNT(HttpStatus.BAD_REQUEST, 2401, "이미 연결된 소셜 계정(provider)입니다."),
    SOCIAL_ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, 2402, "소셜 계정을 찾을 수 없습니다."),

    // Term 관련
    DUPLICATE_TERM_VERSION(HttpStatus.BAD_REQUEST, 2501, "이미 존재하는 약관 버전입니다."),
    TERM_NOT_FOUND(HttpStatus.NOT_FOUND, 2502, "해당 약관 정보를 찾을 수 없습니다."),
    INACTIVE_TERM(HttpStatus.BAD_REQUEST, 2503, "비활성화된 약관입니다."),

    // MemberTerm 관련
    ALREADY_AGREED(HttpStatus.BAD_REQUEST, 2601, "이미 동의된 약관입니다."),
    MEMBER_TERM_NOT_FOUND(HttpStatus.NOT_FOUND, 2602, "해당 회원약관 정보를 찾을 수 없습니다."),
    REQUIRED_TERM_NOT_AGREED(HttpStatus.BAD_REQUEST, 2603, "필수 약관에 동의해야 합니다.");



    private final HttpStatus status; // HTTP 상태 코드
    private final int code; // 고유 오류 코드
    private final String message; // 오류 메시지
}
