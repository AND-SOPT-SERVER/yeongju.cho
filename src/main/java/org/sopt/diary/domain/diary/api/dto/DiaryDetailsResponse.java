package org.sopt.diary.domain.diary.api.dto;

import org.sopt.diary.domain.diary.core.Diary;
import org.sopt.diary.domain.diary.core.Category;

import java.time.LocalDateTime;

public record DiaryDetailsResponse(
        Long id,
        String title,
        String content,
        Category category,
        LocalDateTime createdAt,
        boolean visible
) {
    public static DiaryDetailsResponse from(Diary diary) {
        return new DiaryDetailsResponse(
                diary.getId(), diary.getTitle(), diary.getContent(),
                diary.getCategory(), diary.getCreatedAt(), diary.isVisible()
        );
    }
}
