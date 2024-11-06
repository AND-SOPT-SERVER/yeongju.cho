package org.sopt.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.diary.enums.Category;

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

    @Column(name = "is_visible", nullable = false)
    private boolean visible;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private Diary(Long id,  User user, String title,  String content, Category category, boolean visible){
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.visible = visible;
    }

    public void updateDiary(String content, Category category, boolean visible){
        this.content = content;
        this.category = category;
        this.visible = visible;
    }
}
