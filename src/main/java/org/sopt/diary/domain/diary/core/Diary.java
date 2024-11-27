package org.sopt.diary.domain.diary.core;

import java.time.LocalDateTime;

public class Diary {
    private final long id;
    private final long userId;
    private final String title;
    private final String content;
    private final Category category;
    private final boolean visible;
    private final LocalDateTime createdAt;
    private final int contentLength;

    public Diary(long id, long userId, String title, String content, Category category, boolean visible, LocalDateTime createdAt, int contentLength) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.visible = visible;
        this.createdAt = createdAt;
        this.contentLength = contentLength;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
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

    public boolean isVisible() {
        return visible;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Diary fromEntity(final DiaryEntity diaryEntity) {
        return new Diary(
                diaryEntity.getId(), diaryEntity.getUserId(), diaryEntity.getTitle(), diaryEntity.getContent(),
                diaryEntity.getCategory(), diaryEntity.isVisible(), diaryEntity.getCreatedAt(), diaryEntity.getContentLength()
        );
    }
}
