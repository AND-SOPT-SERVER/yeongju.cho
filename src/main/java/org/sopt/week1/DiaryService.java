package org.sopt.week1;

import java.util.List;
import org.sopt.week1.Main.UI.IdNotExistException;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body) {
        DiaryValidator.validate(body);

        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

    void deleteDiary(final String id) {
        final Long diaryId = Long.parseLong(id);

        if (diaryRepository.existById(diaryId)) {
            diaryRepository.delete(diaryId);
        } else {
            throw new IdNotExistException();
        }
    }

    void patchDiary(final String id, final String body) {
        DiaryValidator.validate(body);

        final Long diaryId = Long.parseLong(id);
        if (diaryRepository.existById(diaryId)) {
            diaryRepository.patch(diaryId, body);
        } else {
            throw new IdNotExistException();
        }
    }
}
