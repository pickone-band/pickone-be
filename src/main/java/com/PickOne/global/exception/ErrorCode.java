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

    USER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "해당 회원의 정보를 찾을 수 없습니다."),
    AUTHORIZATION_DENIED(HttpStatus.FORBIDDEN, 2504, "권한이 없습니다."),

    PREFERENCE_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "해당 회원의 취향정보를 찾을 수 없습니다.");

    private final HttpStatus status; // HTTP 상태 코드
    private final int code; // 고유 오류 코드
    private final String message; // 오류 메시지
}
