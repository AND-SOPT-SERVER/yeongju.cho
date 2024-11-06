package org.sopt.diary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.diary.exception.ErrorCode;
import org.sopt.diary.exception.IllegalArgumentException;

@Getter
@AllArgsConstructor
public enum Category {
    FOOD("음식"),
    SCHOOL("학교"),
    MOVIE("영화"),
    EXERCISE("운동");

    private final String content;

    public static Category fromContent(String content) {
        for (Category category : Category.values()) {
            if (category.getContent().equals(content)) {
                return category;
            }
        }
        throw new IllegalArgumentException(ErrorCode.INVALID_ARGUMENTS);
    }
}