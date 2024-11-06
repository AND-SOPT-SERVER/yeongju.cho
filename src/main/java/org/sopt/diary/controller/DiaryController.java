package org.sopt.diary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.diary.enums.Category;
import org.sopt.diary.dto.request.DiaryDetailsDto;
import org.sopt.diary.dto.request.DiaryCreateDto;
import org.sopt.diary.dto.request.DiaryUpdateDto;
import org.sopt.diary.dto.response.DiaryListResponse;
import org.sopt.diary.dto.response.DiaryMeListResponse;
import org.sopt.diary.enums.SortOption;
import org.sopt.diary.service.Diary.DiaryService;
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
            @RequestHeader("Authorization") final Long userId,
            @RequestBody @Valid final DiaryCreateDto diaryCreateDto
            ) {
        return ResponseEntity.created(URI.create(
                diaryService.createDiary(userId, diaryCreateDto).getId().toString()
        )).build();
    }

    // 일기 상세 조회
    @GetMapping("/diary/{diaryId}")
    public ResponseEntity<DiaryDetailsDto> getDiary(
            @RequestHeader("Authorization") final Long userId,
            @PathVariable final Long diaryId
    ){
        return ResponseEntity.ok(diaryService.getDiaryDetails(userId, diaryId));
    }

    // 전체 일기 목록 조회
    @GetMapping("/diary")
    public ResponseEntity<DiaryListResponse> getDiaryList(
            @RequestParam(name = "category", required = false) final Category category,
            @RequestParam(name = "sort", required = false) final SortOption sortOption,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
            ){
        Category categoryContent = null;
        if (category != null) {
            categoryContent = Category.fromContent(category.getContent());
        }

        SortOption sortOptionContent = null;
        if (sortOption != null) {
            sortOptionContent = SortOption.fromContent(sortOption.getContent());
        }
        return ResponseEntity.ok(diaryService.getDiaryList(categoryContent, sortOptionContent, page, size));
    }

    // 내 일기 목록 조회
    @GetMapping("/diary/me")
    public ResponseEntity<DiaryMeListResponse> getDiaryMeList(
            @RequestHeader("Authorization") final Long userId,
            @RequestParam(name = "category", required = false) final Category category,
            @RequestParam(name = "sort", required = false) final SortOption sortOption,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
    ){
        Category categoryContent = null;
        if (category != null) {
            categoryContent = Category.fromContent(category.getContent());
        }

        SortOption sortOptionContent = null;
        if (sortOption != null) {
            sortOptionContent = SortOption.fromContent(sortOption.getContent());
        }
        return ResponseEntity.ok(diaryService.getDiaryMeList(userId, categoryContent, sortOptionContent, page, size));
    }

    // 일기 수정
    @PatchMapping("/diary/{diaryId}")
    public ResponseEntity<Void> updateDiary(
            @RequestHeader("Authorization") final Long userId,
            @PathVariable final Long diaryId,
            @RequestBody @Valid final DiaryUpdateDto diaryUpdateDto
    ){
        diaryService.updateDiary(userId, diaryId, diaryUpdateDto);
        return ResponseEntity.noContent().build();
    }

    // 일기 제거
    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @RequestHeader("Authorization") final Long userId,
            @PathVariable final Long diaryId
    ) {
        diaryService.removeDiary(userId, diaryId);
        return ResponseEntity.noContent().build();
    }
}
