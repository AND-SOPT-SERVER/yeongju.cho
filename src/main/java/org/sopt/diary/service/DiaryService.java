package org.sopt.diary.service;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.Repository.DiaryRepository;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.dto.request.DiaryCreateDto;
import org.sopt.diary.dto.request.DiaryDetailsDto;
import org.sopt.diary.dto.request.DiaryUpdateDto;
import org.sopt.diary.dto.response.DiaryListResponse;
import org.sopt.diary.exception.ErrorCode;
import org.sopt.diary.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Transactional
    public Diary createDiary(DiaryCreateDto diaryCreateDto){
        Diary diary = Diary.builder()
                .title(diaryCreateDto.title())
                .content(diaryCreateDto.content())
                .build();
        return diaryRepository.save(diary);
    }

    private Diary findById(final Long diaryId){
        return diaryRepository.findById(diaryId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_DIARY)
        );
    }

    public DiaryDetailsDto getDiaryDetails(final Long diaryId){
        Diary diary = findById(diaryId);

        return DiaryDetailsDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .createdAt(diary.getCreatedAt())
                .build();
    }

    public DiaryListResponse getDiaryList(){
        List<DiaryListResponse.DiaryDto> diaryItems = diaryRepository.findTop10ByOrderByCreatedAtDesc()
                .stream().map(
                        diary -> DiaryListResponse.DiaryDto.builder()
                                .id(diary.getId())
                                .title(diary.getTitle())
                                .build()
                ).toList();
        return DiaryListResponse.builder().diaryLists(diaryItems).build();
    }

    @Transactional
    public void updateDiary(final Long diaryId, DiaryUpdateDto diaryUpdateDto){
        Diary diary = findById(diaryId);
        diary.updateDiary(diaryUpdateDto.content());
    }

    @Transactional
    public void removeDiary(final Long diaryId){
        Diary diary = findById(diaryId);
        diaryRepository.delete(diary);
    }
}