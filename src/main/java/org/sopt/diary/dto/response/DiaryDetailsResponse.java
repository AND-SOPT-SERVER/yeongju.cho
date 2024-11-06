package org.sopt.diary.dto.response;

import lombok.Builder;
import org.sopt.diary.enums.Category;

import java.time.LocalDateTime;

@Builder
public record DiaryDetailsResponse(
        Long id,
        String title,
        String content,
        Category category,
        LocalDateTime createdAt,
        boolean visible
) {
}
