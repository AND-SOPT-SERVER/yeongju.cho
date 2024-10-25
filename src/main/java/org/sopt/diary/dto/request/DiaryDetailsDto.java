package org.sopt.diary.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DiaryDetailsDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt
) {
}
