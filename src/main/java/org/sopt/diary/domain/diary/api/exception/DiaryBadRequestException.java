package org.sopt.diary.domain.diary.api.exception;

import org.springframework.http.HttpStatus;

public class DiaryBadRequestException extends DiaryApiException {
    public DiaryBadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
