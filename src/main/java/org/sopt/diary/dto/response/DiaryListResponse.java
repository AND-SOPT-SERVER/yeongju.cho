package org.sopt.diary.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DiaryListResponse(
        List<DiaryDto> diaryLists
) {

    @Builder
    public record DiaryDto(
            Long id,
            Long userId,
            String title,
            String nickname,
            LocalDateTime createdAt
    ){
    }
}
