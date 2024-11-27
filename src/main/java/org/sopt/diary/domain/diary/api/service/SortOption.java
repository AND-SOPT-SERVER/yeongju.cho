package org.sopt.diary.domain.diary.api.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.sopt.diary.domain.diary.core.DiaryTableConstants.COLUMN_CONTENT_LENGTH;
import static org.sopt.diary.domain.diary.core.DiaryTableConstants.COLUMN_ID;

@Getter
@AllArgsConstructor
public enum SortOption {
    RECENT_DATE(Sort.Order.desc(COLUMN_ID)),
    CONTENT_LENGTH_DESC(Sort.Order.desc(COLUMN_CONTENT_LENGTH));

    private final Sort.Order order;

    SortOption(Sort.Order order) {
        this.order = order;
    }

    public Sort.Order getOrder() {
        return order;
    }
}