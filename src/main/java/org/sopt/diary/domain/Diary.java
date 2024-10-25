package org.sopt.diary.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    public LocalDateTime createdAt;

    public String content;

    @Builder
    private Diary(Long id, String title,  String content, LocalDateTime createdAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public void updateDiary(String content){
        this.content = content;
    }
}
