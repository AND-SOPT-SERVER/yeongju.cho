package org.sopt.diary.service.Diary;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.repository.DiaryRepository;
import org.sopt.diary.domain.Diary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryRemover {

    private final DiaryRepository diaryRepository;

    public void deleteDiary(final Diary diary){
        diaryRepository.delete(diary);
    }
}