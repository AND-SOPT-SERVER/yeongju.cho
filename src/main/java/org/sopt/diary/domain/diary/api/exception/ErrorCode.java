package org.sopt.diary.domain.diary.api.exception;

enum ErrorCode {
    // 400
    BAD_REQUEST_INVALID_CATEGORY("00", "유효하지 않은 카테고리입니다."),
    BAD_REQUEST_INVALID_CURSOR_RECENT_DATE("01", "recent date 순 조회시 유효하지 않은 커서입니다."),
    BAD_REQUEST_INVALID_CURSOR_CONTENT_LENGTH("02", "content length 순 조회시 유효하지 않은 커서입니다"),

    // 409
    CONFLICT_DUPLICATE_TITLE("00", "중복되는 제목입니다."),
    CONFLICT_CREATE_LIMIT("01", "작성 시간이 제한됩니다."),
    ;

    private final String code;
    private final String detail;

    ErrorCode(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public String getDetail() {
        return detail;
    }
}
