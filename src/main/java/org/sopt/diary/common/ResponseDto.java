package org.sopt.diary.common;

public record ResponseDto<T> (
        String code,
        T data,
        String message
) {
    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<>(code, null, message);
    }

    // Validation 실패 시 응답 형식
    public static <T> ResponseDto<T> failValidate(final T data) {
        return new ResponseDto<>("fail", data, null);
    }

    // 후에 아래처럼 success 에 대해서도 공통된 응답 형식을 반환할 수 있도록 하면 좋은 코드가 될 것 같다.
    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>("success", data, null);
    }

}
