package org.sopt.week1;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.sopt.week1.Main.UI.LimitEditException;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final Map<Long, String> deletedStorage = new ConcurrentHashMap<>();
    private final Map<Long, Integer> patchCount = new ConcurrentHashMap<>(); //수정 횟수
    private final Map<Long, LocalDate> patchDate = new ConcurrentHashMap<>(); // 마지막으로 수정한 날짜

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary){

        // 채번 과정(1을 더한 값을 반환)
        final long id = numbering.addAndGet(1);

        // 저장 과정
        storage.put(id, diary.getBody());
        patchCount.put(id, 0);
        patchDate.put(id, LocalDate.now());
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
        String removedDiary = storage.remove(id);
        if(removedDiary != null){
            deletedStorage.put(id, removedDiary);
            patchCount.remove(id);  // 삭제 시 수정 횟수 제거
            patchDate.remove(id);  // 삭제 시 날짜 제거
        }
    }

    void patch(final Long id, final String body) {
        LocalDate today = LocalDate.now();
        LocalDate last = patchDate.getOrDefault(id, today);

        if(!today.equals(last)){
            patchCount.put(id, 0);
            patchDate.put(id, today);
        }

        int count = patchCount.getOrDefault(id, 0);

        if(count >= 2){
            throw new LimitEditException();
        }
        storage.replace(id, body);
        patchCount.put(id, count + 1);
        patchDate.put(id, today);
    }

    void restore(final Long id) {
        String beRestore = deletedStorage.remove(id);
        if (beRestore != null) {
            storage.put(id, beRestore);
            patchCount.put(id, 0);
            patchDate.put(id, LocalDate.now());
        }
        storage.remove(id);
    }

    void patch(final Long id, final String body) {
        /*
        replace() : key 가 존재할 때에만 값을 변경
        put() : key 가 존재하지 않으면 새로운 key-value 쌍을 추가
         */
        storage.replace(id, body);

    }

    boolean existById(final Long id){
        return storage.containsKey(id);
    }


    boolean existByDeletedId(final Long id){
        return deletedStorage.containsKey(id);
    }
}
