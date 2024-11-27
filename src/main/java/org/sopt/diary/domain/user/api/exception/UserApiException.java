package org.sopt.diary.domain.user.api.exception;

import org.sopt.diary.domain.user.UserBaseException;
import org.springframework.http.HttpStatus;

public abstract class UserApiException extends UserBaseException {
    abstract HttpStatus getStatus();
}
