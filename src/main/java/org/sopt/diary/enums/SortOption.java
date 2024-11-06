package org.sopt.diary.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.diary.exception.ErrorCode;
import org.sopt.diary.exception.IllegalArgumentException;

@Getter
@AllArgsConstructor
public enum SortOption {
    RECENT_DATE("createdAt"),
    CONTENT_LENGTH("contentLength");

    private final String content;

    public static SortOption fromContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_ARGUMENTS);
        }

        for (SortOption sortOption : SortOption.values()) {
            if (sortOption.getContent().equals(content)) {
                return sortOption;
            }
        }
        throw new IllegalArgumentException(ErrorCode.INVALID_ARGUMENTS);
    }
}