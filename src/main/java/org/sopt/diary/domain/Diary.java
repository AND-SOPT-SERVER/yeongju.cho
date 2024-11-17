package org.sopt.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.diary.enums.Category;
import org.sopt.diary.exception.DiaryUpdatesLimitException;
import org.sopt.diary.exception.ErrorCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "date", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    @Column(name = "modified_count")
    private int modifiedCount;

    @Column(name = "is_visible", nullable = false)
    private boolean visible;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private Diary(Long id, User user, String title, String content, Category category, boolean visible) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.visible = visible;
        this.lastModifiedDate = null;
        this.modifiedCount = 0;
    }

    public void updateDiary(String content, Category category, boolean visible) {
        LocalDate today = LocalDate.now();

        if (lastModifiedDate == null || !lastModifiedDate.isEqual(today)) {
            this.modifiedCount = 1;
            this.lastModifiedDate = today;
        } else {
            if (modifiedCount >= 2) {
                throw new DiaryUpdatesLimitException(ErrorCode.DIARY_UPDATE_NOT_ALLOWED);
            }
            modifiedCount++;
        }
        this.content = content;
        this.category = category;
        this.visible = visible;
    }
}
