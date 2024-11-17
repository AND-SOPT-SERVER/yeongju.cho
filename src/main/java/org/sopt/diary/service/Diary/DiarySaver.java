package org.sopt.diary.service.Diary;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.repository.DiaryRepository;
import org.sopt.diary.domain.Diary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiarySaver {

    private final DiaryRepository diaryRepository;

    public Diary save(final Diary diary){
        return diaryRepository.save(diary);
    }
}