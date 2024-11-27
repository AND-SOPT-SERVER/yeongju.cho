package org.sopt.diary.domain.diary.api.exception;

import org.sopt.diary.domain.diary.DiaryBaseException;
import org.springframework.http.HttpStatus;

public abstract class DiaryApiException extends DiaryBaseException {
    private final ErrorCode errorCode;

    protected DiaryApiException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    abstract HttpStatus getStatus();
}
