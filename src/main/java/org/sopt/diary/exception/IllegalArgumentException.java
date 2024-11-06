package org.sopt.diary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IllegalArgumentException extends RuntimeException{
    private final ErrorCode errorCode;
}