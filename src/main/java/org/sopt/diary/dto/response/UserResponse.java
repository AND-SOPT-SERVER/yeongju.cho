package org.sopt.diary.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserResponse(
        String username,
        String nickname
) {
}