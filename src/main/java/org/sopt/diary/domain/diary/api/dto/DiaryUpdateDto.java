package org.sopt.diary.domain.diary.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.sopt.diary.domain.diary.core.Category;

@Builder
public record DiaryUpdateDto(
        @Size(max = 30, message = "일기의 내용은 30자 이내여야 합니다.")
        String content,
        Category category,
        Boolean visible
) {
}