package org.sopt.week1;

import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        if (body.length() > 30) { // Q :검증 과정 꼭 컨트롤러에서 해야할까여?
            throw new IllegalArgumentException();  // Q : 왜 이 예외를 던지셨을까여?
        }
        diaryService.writeDiary(body);
    }

    final void delete(final String id) {
        diaryService.deleteDiary(id);
    }

    final void patch(final String id, final String body) {
        if(body.length() > 30){
            throw new IllegalArgumentException();
        }
        diaryService.patchDiary(id, body);
    }

    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}

