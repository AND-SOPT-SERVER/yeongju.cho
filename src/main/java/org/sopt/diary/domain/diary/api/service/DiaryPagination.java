package org.sopt.diary.domain.diary.api.service;

import jakarta.annotation.Nullable;
import org.sopt.diary.domain.diary.core.Diary;
import org.sopt.diary.domain.diary.core.DiaryList;

import java.util.List;

public class DiaryPagination {
    private final int needSize;
    private final DiaryList diaryList;

    public DiaryPagination(DiaryList diaryList, int needSize) {
        this.needSize = needSize;
        if (diaryList == null) {
            // gdh : 내부 구현 오류 -> 클라이언트에게는 500 으로 나가야 정상 값
            throw new IllegalArgumentException();
        }
        this.diaryList = diaryList;
    }

    private boolean noMoreData() {
        return diaryList.size() <= needSize;
    }

    @Nullable
    public Diary next() {
        if (noMoreData()) {
            return null;
        }

        return diaryList.last();
    }

    @Nullable
    public Diary prev() {
        if (noMoreData()) {
            return null;
        }

        return diaryList.first();
    }

    public List<Diary> getDiaryList() {
        return diaryList.getDiaryList();
    }
}
