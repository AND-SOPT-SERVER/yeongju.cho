package org.sopt.diary.domain.user.api.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        String username,
        String nickname
) {
}