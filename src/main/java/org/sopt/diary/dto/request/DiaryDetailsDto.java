package org.sopt.diary.dto.request;

import lombok.Builder;
import org.sopt.diary.enums.Category;

import java.time.LocalDateTime;

@Builder
public record DiaryDetailsDto(
        Long id,
        String title,
        String content,
        Category category,
        LocalDateTime createdAt,
        boolean visible
) {
}
