package org.sopt.diary.domain.diary.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.diary.domain.diary.core.Category;

import java.time.LocalDateTime;

public record DiaryCreateDto(
        Long id,

        @NotNull(message = "제목을 입력해주세요.")
        @Size(max = 10, message = "일기의 제목은 10자 이내여야 합니다.")
        String title,

        @Size(max = 30, message = "일기의 내용은 30자 이내여야 합니다.")
        String content,

        Category category,
        boolean visible,
        LocalDateTime createdAt
) {
}
