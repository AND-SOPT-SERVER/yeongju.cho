package org.sopt.diary.domain.diary.api.dto;

import jakarta.annotation.Nullable;
import org.sopt.diary.domain.diary.api.service.DiaryPagination;
import org.sopt.diary.domain.diary.api.service.SortOption;
import org.sopt.diary.domain.diary.core.Diary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record DiaryListResponse(
        List<DiaryDto> diaryLists,
        // gdh : pagination 에 가장 기본은 cursor 를 내려주는 것입니다
        // : 그래야지 다음 조회를 이어갈 수 있으니까요
        @Nullable
        String next
) {
    public record DiaryDto(
            long id,
            long userId,
            String title,
            String nickname,
            LocalDateTime createdAt
    ) {
        public static DiaryDto from(Diary diary, String nickname) {
            return new DiaryDto(diary.getId(), diary.getUserId(), diary.getTitle(), nickname, diary.getCreatedAt());
        }
    }

    public static DiaryListResponse from(
            SortOption sortOption,
            DiaryPagination diaryPagination,
            Map<Long, String> nicknameMap
    ) {
        final List<DiaryDto> diaryDtoList = diaryPagination.getDiaryList().stream().map(diary -> {
            String nickname = nicknameMap.getOrDefault(diary.getId(), null);
            return new DiaryDto(diary.getId(), diary.getUserId(), diary.getTitle(), nickname, diary.getCreatedAt());
        }).toList();

        final String nextCursor;

        // RECENT_DATE 의 cursor 는 id
        if (sortOption == SortOption.RECENT_DATE) {
            final Diary diary = diaryPagination.next();

            nextCursor = diary != null ? String.valueOf(diary.getId()) : null;
        }
        // CONTENT_LENGTH 의 cursor 는 id-contentLength
        else {
            final Diary diary = diaryPagination.next();

            if (diary == null) {
                nextCursor = null;
            } else {
                nextCursor = new DiaryContentLengthCursor(diary.getId(), diary.getContentLength()).toString();
            }
        }

        return new DiaryListResponse(diaryDtoList, nextCursor);
    }
}
