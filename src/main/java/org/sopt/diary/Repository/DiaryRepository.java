package org.sopt.diary.Repository;


import org.sopt.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findTop10ByOrderByCreatedAtDesc();
}
