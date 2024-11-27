package org.sopt.diary.domain.diary.core;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.domain.diary.core.exception.DiaryCreateLimitException;
import org.sopt.diary.domain.diary.core.exception.DiaryNotFoundException;
import org.sopt.diary.domain.diary.core.exception.DuplicateTitleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.diary.domain.diary.core.DiaryTableConstants.COLUMN_ID;

@Component
@RequiredArgsConstructor
public class DiaryRetriever {

    private final DiaryRepository diaryRepository;

    public DiaryRetriever(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional(readOnly = true)
    public void checkTitleDup(final long userId, final String title) throws DuplicateTitleException {
        diaryRepository.findByUserIdAndTitle(userId, title)
                .ifPresent(existingDiary -> {
                    throw new DuplicateTitleException();
                });
    }

    @Transactional(readOnly = true)
    public void checkLatest5Minute(final long userId) throws DiaryCreateLimitException {
        diaryRepository.findTop1ByUserIdAndIdDesc(userId)
                .ifPresent(existingDiary -> {
                    if (existingDiary.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
                        throw new DiaryCreateLimitException();
                    }
                });
    }

    @Transactional(readOnly = true)
    public Diary findByDiaryId(final long diaryId) {
        final DiaryEntity diaryEntity = diaryRepository.findById(diaryId)
                .orElseThrow(DiaryNotFoundException::new);

        return Diary.fromEntity(diaryEntity);
    }

    public List<Diary> findAllByCategoryAndOrderByCreatedAt(
            final Category category, final Sort.Order order, final long id, final int size
    ) {
        // 기존 조회 사이즈보다 1 추가 -> next 가 있는지 알기 위함
        final int pageSize = size + 1;
        final PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(order));
        final List<DiaryEntity> diaryEntityList;

        if (order.isDescending()) {
            diaryEntityList = diaryRepository.findAllByCategoryAndIdLessThanEqual(category, id, pageRequest);
        } else {
            diaryEntityList = diaryRepository.findAllByCategoryAndIdGreaterThanEqual(category, id, pageRequest);
        }

        return diaryEntityList.stream().map(Diary::fromEntity).toList();
    }

    public List<Diary> findAllByOrderByCreatedAt(final Sort.Order order, final long id, final int size) {
        // 기존 조회 사이즈보다 1 추가 -> next 가 있는지 알기 위함
        final int pageSize = size + 1;
        final PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(order));
        final List<DiaryEntity> diaryEntityList;

        if (order.isDescending()) {
            diaryEntityList = diaryRepository.findAllByIdAndIdLessThanEqual(id, pageRequest);
        } else {
            diaryEntityList = diaryRepository.findAllByIdAndIdGreaterThanEqual(id, pageRequest);
        }

        return diaryEntityList.stream().map(Diary::fromEntity).toList();
    }

    public List<Diary> findAllByOrderByContentLength(
            final Sort.Order order, final Long id, final Integer contentLength, final int size
    ) {
        // 기존 조회 사이즈보다 1 추가 -> next 가 있는지 알기 위함
        // order : content_length desc, id asc
        final int pageSize = size + 1;
        final PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(order, Sort.Order.asc(COLUMN_ID)));
        final List<DiaryEntity> diaryEntityList;

        // 최신부터 조회
        if (contentLength == null) {
            diaryEntityList = diaryRepository.findAllByContentLengthDesc(pageRequest);
        }

        // 특정 위치부터 조회
        else {
            if (order.isDescending()) {
                diaryEntityList = diaryRepository.findAllByContentLengthAndIdAndContentLengthLessThanEqualAndIdGreaterThanEqual(contentLength, id, pageRequest);
            } else {
                diaryEntityList = diaryRepository.findAllByContentLengthAndIdAndContentLengthGreaterThanEqualAndIdLessThanEqual(contentLength, id, pageRequest);
            }
        }

        return diaryEntityList.stream().map(Diary::fromEntity).toList();
    }

    public List<Diary> findAllByCategoryAndOrderByContentLength(
            final Category category, final Sort.Order order, final Long id, final Integer contentLength, final int size
    ) {
        // 기존 조회 사이즈보다 1 추가 -> next 가 있는지 알기 위함
        // order : content_length desc, id asc
        final int pageSize = size + 1;
        final PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by(order, Sort.Order.asc(COLUMN_ID)));
        final List<DiaryEntity> diaryEntityList;

        // 최신부터 조회
        if (contentLength == null) {
            diaryEntityList = diaryRepository.findAllByContentLengthDesc(pageRequest);
        }

        // 특정 위치부터 조회
        else {
            if (order.isDescending()) {
                diaryEntityList = diaryRepository.findAllByCategoryAndContentLengthAndIdAndContentLengthLessThanEqualAndIdGreaterThanEqual(category, contentLength, id, pageRequest);
            } else {
                diaryEntityList = diaryRepository.findAllByCategoryAndContentLengthAndIdAndContentLengthGreaterThanEqualAndIdLessThanEqual(category, contentLength, id, pageRequest);
            }
        }

        return diaryEntityList.stream().map(Diary::fromEntity).toList();
    }

    public Page<DiaryEntity> findByCategoryOrderByContentLengthDesc(final Category category, final Pageable pageable) {
        return diaryRepository.findByCategoryOrderByContentLengthDesc(category, pageable);
    }

    public Page<DiaryEntity> findAllOrderByContentLengthDesc(final Pageable pageable) {
        return diaryRepository.findAllOrderByContentLengthDesc(pageable);
    }

    public Page<DiaryEntity> findAllByOrderByCreatedAtDesc(final Pageable pageable) {
        return diaryRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    /*
    User
     */
    public Page<DiaryEntity> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, Category category, Pageable pageable) {
        return diaryRepository.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category, pageable);
    }

    public Page<DiaryEntity> findByUserIdAndCategoryOrderByContentLengthDesc(Long userId, Category category, Pageable pageable) {
        return diaryRepository.findByUserIdAndCategoryOrderByContentLengthDesc(userId, category, pageable);
    }

    public Page<DiaryEntity> findByUserIdOrderByContentLengthDesc(Long userId, Pageable pageable) {
        return diaryRepository.findByUserIdOrderByContentLengthDesc(userId, pageable);
    }

    public Page<DiaryEntity> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable) {
        return diaryRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }
}