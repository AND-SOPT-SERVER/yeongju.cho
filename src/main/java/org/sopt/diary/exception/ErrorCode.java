package org.sopt.diary.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode{
    NOT_FOUND_DIARY(HttpStatus.NOT_FOUND,"error","존재하지 않는 Diary 입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"error","존재하지 않는 User 입니다."),

    INVALID_ARGUMENTS(HttpStatus.BAD_REQUEST, "error", "인자의 형식이 올바르지 않습니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
