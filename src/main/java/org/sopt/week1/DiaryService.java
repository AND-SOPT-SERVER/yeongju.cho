package org.sopt.week1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    // 일기 작성 기능
    void writeDiary(final String body) {
        if (body.length() > 30) {
           return;
        }
        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);
    }

    // 일기 조회 기능
    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

    // 일기 삭제 기능
    void deleteDiary(final String id) {
        final Long diaryId = Long.parseLong(id);
        diaryRepository.delete(diaryId);
    }

    // 일기 수정 기능
    void patchDiary(final String id, final String body) {
        if(body.length() > 30){
            return;
        }
        final Long diaryId = Long.parseLong(id);
        diaryRepository.patch(diaryId, body);
    }
}
