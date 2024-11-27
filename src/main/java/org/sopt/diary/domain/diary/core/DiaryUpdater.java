package org.sopt.diary.domain.diary.core;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.domain.diary.api.dto.DiaryUpdateDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryUpdater {

    public void updateDiary(
            final DiaryEntity diaryEntity,
            final DiaryUpdateDto diaryUpdateDto
    ) {
        String content = diaryUpdateDto.content() != null ? diaryUpdateDto.content() : diaryEntity.getContent();
        Category category = diaryUpdateDto.category() != null ? diaryUpdateDto.category() : diaryEntity.getCategory();
        boolean visible = diaryUpdateDto.visible() != null ? diaryUpdateDto.visible() : diaryEntity.isVisible();

        diaryEntity.updateDiary(content, category, visible);
    }
}