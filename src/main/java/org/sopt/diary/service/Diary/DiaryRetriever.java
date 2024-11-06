package org.sopt.diary.service.Diary;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.Repository.DiaryRepository;
import org.sopt.diary.enums.Category;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.domain.User;
import org.sopt.diary.exception.ErrorCode;
import org.sopt.diary.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryRetriever {

    private final DiaryRepository diaryRepository;

    public Diary findByUserAndDiaryId(final User user, final Long diaryId) {
        return diaryRepository.findByUserAndId(user, diaryId).orElseThrow(
                ()-> new NotFoundException(ErrorCode.NOT_FOUND_DIARY)
        );
    }

    public Page<Diary> findByCategoryOrderByCreatedAtDesc(final Category category, final Pageable pageable){
        return diaryRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
    }

    public Page<Diary> findByCategoryOrderByContentLengthDesc(final Category category, final Pageable pageable) {
        return diaryRepository.findByCategoryOrderByContentLengthDesc(category, pageable);
    }

    public Page<Diary> findAllOrderByContentLengthDesc(final Pageable pageable) {
        return diaryRepository.findAllOrderByContentLengthDesc(pageable);
    }

    public Page<Diary> findAllByOrderByCreatedAtDesc(final Pageable pageable){
        return diaryRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    /*
    User
     */
    public Page<Diary> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, Category category, Pageable pageable){
        return diaryRepository.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category, pageable);
    }

    public Page<Diary> findByUserIdAndCategoryOrderByContentLengthDesc(Long userId, Category category, Pageable pageable){
        return diaryRepository.findByUserIdAndCategoryOrderByContentLengthDesc(userId, category, pageable);
    }

    public Page<Diary> findByUserIdOrderByContentLengthDesc(Long userId, Pageable pageable){
        return diaryRepository.findByUserIdOrderByContentLengthDesc(userId, pageable);
    }

    public Page<Diary> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable){
        return diaryRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
}