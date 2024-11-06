package org.sopt.diary.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DiaryMeListResponse(
        List<DiaryMeDto> diaryLists
) {
    @Builder
    public record DiaryMeDto(
            Long id,
            String title,
            LocalDateTime createdAt
    ){
    }
}