package org.sopt.diary.domain.diary.api;

import org.sopt.diary.common.ResponseDto;
import org.sopt.diary.domain.diary.api.exception.DiaryApiException;
import org.sopt.diary.domain.diary.api.exception.DiaryConflictException;
import org.sopt.diary.exception.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DiaryConflictException.class)
    public ResponseEntity<ResponseDto<String>> b() {
        return ResponseEntity.status(409);
    }

    @ExceptionHandler(DiaryApiException.class)
    public ResponseEntity<ResponseDto<String>> a() {
        return ResponseEntity.badRequest();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto<String>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(e.getErrorCode().getCode(),e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.failValidate(errors));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<String>> handleException(IllegalArgumentException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(e.getErrorCode().getCode(),e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(DuplicateTitleException.class)
    public ResponseEntity<ResponseDto<String>> handleException(DuplicateTitleException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(e.getErrorCode().getCode(),e.getErrorCode().getMessage()));
    }
}
