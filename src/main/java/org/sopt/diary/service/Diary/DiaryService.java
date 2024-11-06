package org.sopt.diary.service.Diary;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.Repository.DiaryRepository;
import org.sopt.diary.enums.Category;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.enums.SortOption;
import org.sopt.diary.domain.User;
import org.sopt.diary.dto.request.DiaryCreateDto;
import org.sopt.diary.dto.request.DiaryDetailsDto;
import org.sopt.diary.dto.request.DiaryUpdateDto;
import org.sopt.diary.dto.response.DiaryListResponse;
import org.sopt.diary.dto.response.DiaryMeListResponse;
import org.sopt.diary.service.User.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final UserService userService;
    private final DiaryRetriever diaryRetriever;
    private final DiaryRemover diaryRemover;
    private final DiaryUpdater diaryUpdater;
    private final DiarySaver diarySaver;

    // 일기 작성 기능
    @Transactional
    public Diary createDiary(final Long userId, DiaryCreateDto diaryCreateDto){
        User user = userService.findById(userId);

        Diary diary = Diary.builder()
                .user(user)
                .title(diaryCreateDto.title())
                .content(diaryCreateDto.content())
                .category(diaryCreateDto.category())
                .visible(diaryCreateDto.visible())
                .build();
        return diarySaver.save(diary);
    }

    // 일기 상세 조회
    public DiaryDetailsDto getDiaryDetails(final Long userId, final Long diaryId){
        User user = userService.findById(userId);
        Diary diary = diaryRetriever.findByUserAndDiaryId(user, diaryId);

        return DiaryDetailsDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .category(diary.getCategory())
                .createdAt(diary.getCreatedAt())
                .visible(diary.isVisible())
                .build();
    }

    // 일기 목록 조회
    public DiaryListResponse getDiaryList(
            final Category category,
            final SortOption sortOption,
            final int page,
            final int size
    ){
        final Pageable pageable = PageRequest.of(page, size);
        Page<Diary> diaryPage;

        SortOption defaultSortOption = (sortOption != null) ? sortOption : SortOption.RECENT_DATE;

        if (category != null) { // 카테고리 선택 O
            diaryPage = defaultSortOption == SortOption.CONTENT_LENGTH
                    ? diaryRetriever.findByCategoryOrderByContentLengthDesc(category, pageable) // 내용 길이 순 정렬
                    : diaryRetriever.findByCategoryOrderByCreatedAtDesc(category, pageable); // 최신순 정렬
        } else { // 카테고리 선택 X
            diaryPage = defaultSortOption == SortOption.CONTENT_LENGTH
                    ? diaryRetriever.findAllOrderByContentLengthDesc(pageable) // 내용 길이 순 정렬
                    : diaryRetriever.findAllByOrderByCreatedAtDesc(pageable); // 최신순 정렬 (기본값)
        }

        List<DiaryListResponse.DiaryDto> diaryItems = diaryPage.getContent()
                .stream().map(
                        diary -> DiaryListResponse.DiaryDto.builder()
                                .id(diary.getId())
                                .userId(diary.getUser().getId())
                                .nickname(diary.getUser().getNickname())
                                .title(diary.getTitle())
                                .build()
                ).toList();

        return DiaryListResponse.builder()
                .diaryLists(diaryItems)
                .build();
    }

    // 내 일기 목록 조회
    public DiaryMeListResponse getDiaryMeList(
            final Long userId,
            final Category category,
            final SortOption sortOption,
            final int page,
            final int size
    ){
        final Pageable pageable = PageRequest.of(page, size);
        Page<Diary> diaryPage;

        SortOption defaultSortOption = (sortOption != null) ? sortOption : SortOption.RECENT_DATE;

        if (category != null) {
            diaryPage = defaultSortOption == SortOption.CONTENT_LENGTH
                    ? diaryRetriever.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category, pageable)
                    : diaryRetriever. findByUserIdAndCategoryOrderByContentLengthDesc(userId, category, pageable);
        } else {
            diaryPage = defaultSortOption == SortOption.CONTENT_LENGTH
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
    public void updateDiary(final Long userId, final Long diaryId, DiaryUpdateDto diaryUpdateDto){
        User user = userService.findById(userId);
        Diary diary = diaryRetriever.findByUserAndDiaryId(user, userId);
        diaryUpdater.updateDiary(diary, diaryUpdateDto);
    }

    // 일기 제거 기능
    @Transactional
    public void removeDiary(final Long userId, final Long diaryId){
        User user = userService.findById(userId);
        Diary diary = diaryRetriever.findByUserAndDiaryId(user, diaryId);
        diaryRemover.deleteDiary(diary);
    }
}