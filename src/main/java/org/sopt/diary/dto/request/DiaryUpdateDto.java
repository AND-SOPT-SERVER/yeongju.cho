package org.sopt.diary.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DiaryUpdateDto(
        @Size(max = 30, message = "일기의 내용은 30자 이내여야 합니다.")
        String content
) {
}