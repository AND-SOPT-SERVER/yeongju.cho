package org.sopt.week1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary){

        // 채번 과정(1을 더한 값을 반환)
        final long id = numbering.addAndGet(1);

        // 저장 과정
        storage.put(id, diary.getBody());
    }

    List<Diary> findAll() {
        // (1) diary 를 담을 자료구조
        final List<Diary> diaryList = new ArrayList<>();

        // (2) 저장한 값을 불러오는 반복 구조
        for (long index = 1; index <= numbering.intValue(); index++) {
            final String body = storage.get(index);

            // (2-1) 불러온 값을 구성한 자료구조로 이관
            if(body != null) {
                diaryList.add(new Diary(index, body));
            }
        }

        // (3) 불러온 자료구조를 응답
        return diaryList;
    }

    void delete(final Long id) {
        storage.remove(id);
    }

    void patch(final Long id, final String body) {
        //삭제된 일기(일기의 body가 null) 라면 수정 못하고 리턴
        final String dairyBody = storage.get(id);
        if (dairyBody == null) {
            return;
        }
        storage.put(id, body);
    }
}
