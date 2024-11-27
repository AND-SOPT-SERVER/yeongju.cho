package org.sopt.diary.domain.diary.api.service;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.domain.diary.api.DiaryUserFacade;
import org.sopt.diary.domain.diary.api.dto.DiaryCreateDto;
import org.sopt.diary.domain.diary.api.dto.DiaryMeListResponse;
import org.sopt.diary.domain.diary.api.dto.DiaryUpdateDto;
import org.sopt.diary.domain.diary.api.exception.DiaryBadRequestException;
import org.sopt.diary.domain.diary.api.exception.DiaryConflictException;
import org.sopt.diary.domain.diary.api.exception.DiaryForbiddenException;
import org.sopt.diary.domain.diary.core.*;
import org.sopt.diary.domain.diary.core.exception.DiaryCreateLimitException;
import org.sopt.diary.domain.user.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.sopt.diary.domain.diary.api.exception.ErrorCode.BAD_REQUEST_INVALID_CATEGORY;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryUserFacade diaryUserFacade;
    private final DiaryRetriever diaryRetriever;
    private final DiaryRemover diaryRemover;
    private final DiaryUpdater diaryUpdater;
    private final DiarySaver diarySaver;

    public DiaryService(DiaryUserFacade diaryUserFacade, DiaryRetriever diaryRetriever, DiaryRemover diaryRemover, DiaryUpdater diaryUpdater, DiarySaver diarySaver) {
        this.diaryUserFacade = diaryUserFacade;
        this.diaryRetriever = diaryRetriever;
        this.diaryRemover = diaryRemover;
        this.diaryUpdater = diaryUpdater;
        this.diarySaver = diarySaver;
    }

    public long createDiary(final long userId, DiaryCreateDto diaryCreateDto) {
        // 유저 검증
        validateDiaryOwner(userId);

        // 최근 작성 시간 검사
        try {
            diaryRetriever.checkLatest5Minute(userId);
        } catch (DiaryCreateLimitException e) {
            throw new DiaryConflictException(); // gdh : api 수준의 예외로 변환
        }

        final Diary createdDiary;

        // 제목 중복 검사
        // gdh : 기존에 find 해서 조회하는 로직 제거 -> db 수준에서 중복을 검증해줌 (unique key 로)
        // gdh : unique key error 발생시 DB 에서 예외발생, Application 에서 throw DataIntegrityViolationException
        try {
            createdDiary = diarySaver.save(userId, diaryCreateDto.title(), diaryCreateDto.content(), diaryCreateDto.category(), diaryCreateDto.visible());
        } catch (DataIntegrityViolationException e) {
            throw new DiaryConflictException(); // gdh : api 수준의 예외로 변환
        }

        return createdDiary.getId();
    }

    public Diary getMyDiaryDetails(final long userId, final long diaryId) {
        // 유저 검증
        validateDiaryOwner(userId);

        // 일기 생성
        return diaryRetriever.findByDiaryId(diaryId);
    }

    public DiaryPagination getDiaryWithRecentDate(
            final Category category, final Sort.Order order, final long id, final int size
    ) {
        final List<Diary> diaryList;

        if (category == Category.UNKNOWN) {
            diaryList = diaryRetriever.findAllByOrderByCreatedAt(order, id, size);
        } else {
            diaryList = diaryRetriever.findAllByCategoryAndOrderByCreatedAt(category, order, id, size);
        }

        return new DiaryPagination(new DiaryList(diaryList), size);
    }

    public DiaryPagination getDiaryWithContentLength(
            final Category category,final Sort.Order order,  final Long id, final Integer contentLength, final int size
    ) {
        final List<Diary> diaryList;

        if (category == Category.UNKNOWN) {
            diaryList = diaryRetriever.findAllByOrderByContentLength(order, id, contentLength, size);
        } else {
            diaryList = diaryRetriever.findAllByCategoryAndOrderByContentLength(category, order, id, contentLength, size);
        }

        return new DiaryPagination(new DiaryList(diaryList), size);
    }

    // 내 일기 목록 조회
    public DiaryMeListResponse getDiaryMeList(
            final Long userId,
            final Category category,
            final SortOption sortOption,
            final int page,
            final int size
    ) {
        final Pageable pageable = PageRequest.of(page, size);
        Page<DiaryEntity> diaryPage;

        SortOption defaultSortOption = (sortOption != null) ? sortOption : SortOption.RECENT_DATE;

        if (category != null) {
            diaryPage = defaultSortOption == SortOption.CONTENT_LENGTH_DESC
                    ? diaryRetriever.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category, pageable)
                    : diaryRetriever.findByUserIdAndCategoryOrderByContentLengthDesc(userId, category, pageable);
        } else {
            diaryPage = defaultSortOption == SortOption.CONTENT_LENGTH_DESC
                    ? diaryRetriever.findByUserIdOrderByContentLengthDesc(userId, pageable)
                    : diaryRetriever.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        }

        List<DiaryMeListResponse.DiaryMeDto> diaryItems = diaryPage.getContent()
                .stream().map(
                        diary -> DiaryMeListResponse.DiaryMeDto.builder()
                                .id(diary.getId())
                                .title(diary.getTitle())
                                .build()
                ).toList();

        return DiaryMeListResponse.builder()
                .diaryLists(diaryItems)
                .build();
    }

    // 일기 수정 기능
    @Transactional
    public void updateDiary(final Long userId, final Long diaryId, DiaryUpdateDto diaryUpdateDto) {
        User user = userService.findById(userId);
        DiaryEntity diaryEntity = diaryRetriever.findByDiaryId(user, userId);
        diaryUpdater.updateDiary(diaryEntity, diaryUpdateDto);
    }

    // 일기 제거 기능
    @Transactional
    public void removeDiary(final Long userId, final Long diaryId) {
        User user = userService.findById(userId);
        DiaryEntity diaryEntity = diaryRetriever.findByDiaryId(user, diaryId);
        diaryRemover.deleteDiary(diaryEntity);
    }

    private void validateDiaryOwner(final long userId) {
        if (!diaryUserFacade.isValidUser(userId)) {
            throw new DiaryForbiddenException(); // api 수준의 예외로 변환
        }
    }
}