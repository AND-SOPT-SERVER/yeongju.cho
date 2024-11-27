package org.sopt.diary.domain.diary.core;

import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiaryList {
    private final List<Diary> diaryList;

    public DiaryList(List<Diary> diaryList) {
        // gdh : this.diaryList 의 not null 보장을 생성자에서부터 보장
        this.diaryList = Objects.requireNonNullElseGet(diaryList, ArrayList::new);
    }

    @Nullable
    public Diary first() {
        return diaryList.stream()
                .findFirst()
                .orElseGet(() -> null);
    }

    @Nullable
    public Diary last() {
        if (diaryList.isEmpty()) {
            return null;
        }

        final int lastIndex = diaryList.size() - 1;
        return diaryList.get(lastIndex);
    }

    public int size() {
        return diaryList.size();
    }

    public boolean isEmpty() {
        return diaryList.isEmpty();
    }

    public List<Diary> getDiaryList() {
        return diaryList;
    }
}
