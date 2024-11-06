package org.sopt.diary.Repository;


import org.sopt.diary.enums.Category;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Optional<Diary> findByUserAndId(final User user, final Long diaryId);

    // 카테고리 선택O, 최신순 정렬
    Page<Diary> findByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);

    // 카테고리 선택O, 길이순 정렬
    @Query("SELECT d FROM Diary d WHERE d.category = :category " +
                    "ORDER BY LENGTH(d.content) DESC"
    )
    Page<Diary> findByCategoryOrderByContentLengthDesc(@Param("category") Category category, Pageable pageable);

    // 카테고리 선택X, 길이순 정렬
    @Query("SELECT d FROM Diary d ORDER BY LENGTH(d.content) DESC")
    Page<Diary> findAllOrderByContentLengthDesc(Pageable pageable);

    // 카테고리 선택X, 최신순 정렬(디폴트)
    Page<Diary> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /*
    User 추가
     */
    Page<Diary> findByUserIdAndCategoryOrderByCreatedAtDesc(Long userId, Category category, Pageable pageable);

    @Query("SELECT d FROM Diary d WHERE d.user.id = :userId " +
            "AND d.category = :category ORDER BY LENGTH(d.content) DESC")
    Page<Diary> findByUserIdAndCategoryOrderByContentLengthDesc(@Param("userId") Long userId, @Param("category") Category category, Pageable pageable);

    @Query("SELECT d FROM Diary d WHERE d.user.id = :userId " +
            "ORDER BY LENGTH(d.content) DESC")
    Page<Diary> findByUserIdOrderByContentLengthDesc(@Param("userId") Long userId, Pageable pageable);

    Page<Diary> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
