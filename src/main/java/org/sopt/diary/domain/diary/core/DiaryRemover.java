package org.sopt.diary.domain.diary.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryRemover {

    private final DiaryRepository diaryRepository;

    public void deleteDiary(final DiaryEntity diaryEntity){
        diaryRepository.delete(diaryEntity);
    }
}