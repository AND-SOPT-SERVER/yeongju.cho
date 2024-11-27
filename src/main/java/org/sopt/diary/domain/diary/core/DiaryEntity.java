package org.sopt.diary.domain.diary.core;

import jakarta.persistence.*;
import lombok.Builder;
import org.sopt.diary.exception.DiaryCreateLimitException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.sopt.diary.domain.diary.core.DiaryTableConstants.*;

@Table(
        name = TABLE_DIARY,
        indexes = {
                // DDL 시 인덱스 생성을 위함  : 특정 유저의 특정 제목의 일기
                // https://github.com/AND-SOPT-SERVER/forum/issues/51#issuecomment-2497454005
                @Index(name = "uk1", columnList = "user_id, title", unique = true),

                // DDL 시 인덱스 생성을 위함 : 특정 유저의 content 길이 순
                @Index(name = "idx1", columnList = "user_id, content_length desc")
        }
)
@Entity
public class DiaryEntity {
    @Id
    @Column(name = COLUMN_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = COLUMN_TITLE, nullable = false)
    private String title;

    @Column(name = COLUMN_CONTENT)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_CATEGORY, nullable = false)
    private Category category;

    @Column(name = COLUMN_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = COLUMN_LAST_MODIFIED_AT)
    private LocalDate lastModifiedDate;

    @Column(name = COLUMN_MODIFIED_COUNT)
    private int modifiedCount;

    @Column(name = COLUMN_CONTENT_LENGTH)
    private int contentLength;

    @Column(name = COLUMN_VISIBLE, nullable = false)
    private boolean visible;

    @Column(name = COLUMN_USER_ID, nullable = false)
    private long userId;

    // gdh: JPA proxy 를 위해 기본 생성자 private 으로 구성
    private DiaryEntity() {

    }

    @Builder
    public DiaryEntity(long userId, String title, String content, Category category, boolean visible) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.visible = visible;
        this.lastModifiedDate = null;
        this.modifiedCount = 0;
        this.contentLength = content.length(); // 이모지 등 정확한 length 를 위해 별도 처리 필요!
    }

    public void updateDiary(String content, Category category, boolean visible) {
        LocalDate today = LocalDate.now();

        if (lastModifiedDate == null || !lastModifiedDate.isEqual(today)) {
            this.modifiedCount = 1;
            this.lastModifiedDate = today;
        } else {
            if (modifiedCount >= 2) {
                throw new DiaryCreateLimitException();
            }
            modifiedCount++;
        }
        this.content = content;
        this.category = category;
        this.visible = visible;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getContentLength() {
        return contentLength;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public int getModifiedCount() {
        return modifiedCount;
    }

    public boolean isVisible() {
        return visible;
    }

    public long getUserId() {
        return userId;
    }
}
