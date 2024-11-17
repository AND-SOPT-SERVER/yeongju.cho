package org.sopt.diary.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.diary.exception.ErrorCode;

@Getter
@RequiredArgsConstructor
public class DiaryUpdatesLimitException extends RuntimeException {
    private final ErrorCode errorCode;
}