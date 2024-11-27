package org.sopt.diary.domain.diary.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.diary.domain.diary.api.DiaryUserFacade;
import org.sopt.diary.domain.diary.api.dto.*;
import org.sopt.diary.domain.diary.api.exception.DiaryBadRequestException;
import org.sopt.diary.domain.diary.api.service.DiaryPagination;
import org.sopt.diary.domain.diary.api.service.DiaryService;
import org.sopt.diary.domain.diary.api.service.SortOption;
import org.sopt.diary.domain.diary.core.Category;
import org.sopt.diary.domain.diary.core.Diary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static org.sopt.diary.domain.diary.api.exception.ErrorCode.BAD_REQUEST_INVALID_CURSOR_CONTENT_LENGTH;
import static org.sopt.diary.domain.diary.api.exception.ErrorCode.BAD_REQUEST_INVALID_CURSOR_RECENT_DATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DiaryController {

    private final DiaryService diaryService;
    private final DiaryUserFacade diaryUserFacade;

    public DiaryController(DiaryService diaryService, DiaryUserFacade diaryUserFacade) {
        this.diaryService = diaryService;
        this.diaryUserFacade = diaryUserFacade;
    }

    // 일기 작성
    @PostMapping("/diary")
    public ResponseEntity<Void> createDiary(
            @RequestHeader("Authorization") final long userId,
            @RequestBody @Valid final DiaryCreateDto diaryCreateDto
    ) {
        long diaryId = diaryService.createDiary(userId, diaryCreateDto);

        return ResponseEntity.created(URI.create(Long.valueOf(diaryId).toString())).build();
    }

    // 일기 상세 조회
    @GetMapping("/diary/{diaryId}")
    public ResponseEntity<DiaryDetailsResponse> getDiary(
            @RequestHeader("Authorization") final long userId,
            @PathVariable final long diaryId
    ) {
        final Diary myDiary = diaryService.getMyDiaryDetails(userId, diaryId);

        return ResponseEntity.ok(DiaryDetailsResponse.from(myDiary));
    }

    // 전체 일기 목록 조회
    // : cursor 기반 페이징
    @GetMapping("/diary")
    public ResponseEntity<DiaryListResponse> getDiaryList(
            @RequestParam(name = "category", required = false) final Category category,
            @RequestParam(name = "sort", required = false) SortOption sortOption,
            @RequestParam(defaultValue = "0", required = false) final String cursor,
            @RequestParam(defaultValue = "10") final int size
    ) {
        final DiaryPagination diaryPagination;

        // default 값 세팅
        if (sortOption == null) {
            sortOption = SortOption.RECENT_DATE;
        }

        // RECENT_DATE 일때 조회
        if (sortOption == SortOption.RECENT_DATE) {
            final long id;

            try {
                id = Long.parseLong(cursor);
            } catch (NumberFormatException e) {
                throw new DiaryBadRequestException(BAD_REQUEST_INVALID_CURSOR_RECENT_DATE);
            }

            diaryPagination = diaryService.getDiaryWithRecentDate(
                    category == null ? Category.UNKNOWN : category,
                    sortOption.getOrder(), id, size
            );
        }

        // CONTENT_LENGTH 일때 조회, cursor 를 분해시켜줘야한다.
        else {
            final long id;
            final int contentLength;

            try {
                final DiaryContentLengthCursor diaryContentLengthCursor = DiaryContentLengthCursor.fromString(cursor);

                id = diaryContentLengthCursor.getId();
                contentLength = diaryContentLengthCursor.getContentLength();
            } catch (DiaryContentLengthCursor.InvalidCursorException e) {
                throw new DiaryBadRequestException(BAD_REQUEST_INVALID_CURSOR_CONTENT_LENGTH);
            }

            diaryPagination = diaryService.getDiaryWithContentLength(
                    category == null ? Category.UNKNOWN : category,
                    sortOption.getOrder(), id, contentLength, size
            );
        }

        // nickname 은 User Domain 의 영역, Facade 를 통해 User 의 nickname 은 별도 조회
        final Map<Long, String> writerNickname = diaryUserFacade.findWriterNickname(diaryPagination.getDiaryList());

        return ResponseEntity.ok(DiaryListResponse.from(sortOption, diaryPagination, writerNickname));
    }

    // 내 일기 목록 조회
    @GetMapping("/diary/me")
    public ResponseEntity<DiaryMeListResponse> getDiaryMeList(
            @RequestHeader("Authorization") final Long userId,
            @RequestParam(name = "category", required = false) final Category category,
            @RequestParam(name = "sort", required = false) final SortOption sortOption,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size
    ) {
        return ResponseEntity.ok(
                diaryService.getDiaryMeList(
                        userId,
                        convertCategory(category),
                        convertSortOption(sortOption),
                        page, size
                )
        );
    }

    // 일기 수정
    @PatchMapping("/diary/{diaryId}")
    public ResponseEntity<Void> updateDiary(
            @RequestHeader("Authorization") final Long userId,
            @PathVariable final Long diaryId,
            @RequestBody @Valid final DiaryUpdateDto diaryUpdateDto
    ) {
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
