package org.sopt.diary.domain.diary.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DiarySaver {
    private final DiaryRepository diaryRepository;

    public DiarySaver(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public Diary save(final long userId, final String title, final String content, final Category category, final boolean isVisible) {
        final DiaryEntity diaryEntity = diaryRepository.save(new DiaryEntity(userId, title, content, category, isVisible));

        return Diary.fromEntity(diaryEntity);
    }
}