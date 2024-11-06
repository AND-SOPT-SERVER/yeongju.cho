package org.sopt.diary.service.Diary;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.enums.Category;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.dto.request.DiaryUpdateDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryUpdater {

    public void updateDiary(
            final Diary diary,
            final DiaryUpdateDto diaryUpdateDto
    ) {
        String content = diaryUpdateDto.content() != null ? diaryUpdateDto.content() : diary.getContent();
        Category category = diaryUpdateDto.category() != null ? diaryUpdateDto.category() : diary.getCategory();
        boolean visible = diaryUpdateDto.visible() != null ? diaryUpdateDto.visible() : diary.isVisible();

        diary.updateDiary(content, category, visible);
    }
}