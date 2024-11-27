package org.sopt.diary.domain.diary.core;


import org.sopt.diary.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    Optional<DiaryEntity> findByUserAndTitle(final User user, final String title);

    // UK1 을 통해 특정 유저, 특정 제목의 일기는 단 1개라는 것이 DB 수준에서 보장이 됨.
    Optional<DiaryEntity> findByUserIdAndTitle(final long userId, final String title);

    // id 는 pk 이자, auto_increment 값으로 생성순으로 정렬됨. -> 특정 유저의 desc 로 가장 id 가 높은 일기는 가장 최근 일기
    Optional<DiaryEntity> findTop1ByUserIdAndIdDesc(final long userId);

    List<DiaryEntity> findAllByIdAndIdLessThanEqual(long id, Pageable pageable);

    List<DiaryEntity> findAllByIdAndIdGreaterThanEqual(long id, Pageable pageable);

    List<DiaryEntity> findAllByCategoryAndIdLessThanEqual(Category category, long id, Pageable pageable);

    List<DiaryEntity> findAllByCategoryAndIdGreaterThanEqual(Category category, long id, Pageable pageable);

    List<DiaryEntity> findAllByContentLengthDesc(Pageable pageable);

    List<DiaryEntity> findAllByContentLengthAndIdAndContentLengthLessThanEqualAndIdGreaterThanEqual(int contentLength, long id, Pageable pageable);

    List<DiaryEntity> findAllByContentLengthAndIdAndContentLengthGreaterThanEqualAndIdLessThanEqual(int contentLength, long id, Pageable pageable);

    List<DiaryEntity> findAllByCategoryAndContentLengthAndIdAndContentLengthLessThanEqualAndIdGreaterThanEqual(Category category, int contentLength, long id, Pageable pageable);

    List<DiaryEntity> findAllByCategoryAndContentLengthAndIdAndContentLengthGreaterThanEqualAndIdLessThanEqual(Category category, int contentLength, long id, Pageable pageable);

    Optional<DiaryEntity> findLatestByUser(final long userId);

    // 카테고리 선택O, 최신순 정렬
    Page<DiaryEntity> findByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);

    // 카테고리 선택O, 길이순 정렬
    @Query("SELECT d FROM Diary d WHERE d.category = :category " +
            "ORDER BY LENGTH(d.content) DESC"
    )
    Page<DiaryEntity> findByCategoryOrderByContentLengthDesc(@Param("category") Category category, Pageable pageable);

    // 카테고리 선택X, 길이순 정렬
    @Query("SELECT d FROM Diary d ORDER BY LENGTH(d.content) DESC")
    Page<DiaryEntity> findAllOrderByContentLengthDesc(Pageable pageable);

    // 카테고리 선택X, 최신순 정렬(디폴트)
    Page<DiaryEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /*
    User 추가
     */
    Page<DiaryEntity> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, Category category, Pageable pageable);

    @Query("SELECT d FROM Diary d WHERE d.user.id = :userId " +
            "AND d.category = :category ORDER BY LENGTH(d.content) DESC")
    Page<DiaryEntity> findByUserIdAndCategoryOrderByContentLengthDesc(@Param("userId") Long userId, @Param("category") Category category, Pageable pageable);

    @Query("SELECT d FROM Diary d WHERE d.user.id = :userId " +
            "ORDER BY LENGTH(d.content) DESC")
    Page<DiaryEntity> findByUserIdOrderByContentLengthDesc(@Param("userId") Long userId, Pageable pageable);

    Page<DiaryEntity> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
