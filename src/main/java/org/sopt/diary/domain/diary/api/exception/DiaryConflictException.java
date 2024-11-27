package org.sopt.diary.domain.diary.api.exception;

import org.springframework.http.HttpStatus;

public class DiaryConflictException extends DiaryApiException {
    @Override
    HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
