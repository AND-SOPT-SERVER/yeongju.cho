package org.sopt.diary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.diary.dto.request.DiaryDetailsDto;
import org.sopt.diary.dto.request.DiaryCreateDto;
import org.sopt.diary.dto.request.DiaryUpdateDto;
import org.sopt.diary.dto.response.DiaryListResponse;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {

    private final DiaryService diaryService;

    // 일기 작성
    @PostMapping("/diary")
    public ResponseEntity<Void> createDiary(
            @RequestBody @Valid final DiaryCreateDto diaryCreateDto
            ) {
        return ResponseEntity.created(URI.create(
                diaryService.createDiary(diaryCreateDto).getId().toString()
        )).build();
    }

    // 일기 상세 조회
    @GetMapping("/diary/{diaryId}")
    public ResponseEntity<DiaryDetailsDto> getDiary(
            @PathVariable final Long diaryId
    ){
        return ResponseEntity.ok(diaryService.getDiaryDetails(diaryId));
    }

    // 일기 목록 조회
    @GetMapping("/diary")
    public ResponseEntity<DiaryListResponse> getDiaryList(
    ){
        return ResponseEntity.ok(diaryService.getDiaryList());
    }

    // 일기 수정
    @PatchMapping("/diary/{diaryId}")
    public ResponseEntity<Void> updateDiary(
            @PathVariable final Long diaryId,
            @RequestBody @Valid final DiaryUpdateDto diaryUpdateDto
    ){
        diaryService.updateDiary(diaryId, diaryUpdateDto);
        return ResponseEntity.noContent().build();
    }

    // 일기 제거
    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @PathVariable final Long diaryId
    ) {
        diaryService.removeDiary(diaryId);
        return ResponseEntity.noContent().build();
    }
}
