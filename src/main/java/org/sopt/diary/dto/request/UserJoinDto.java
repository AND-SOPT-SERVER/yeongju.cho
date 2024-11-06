package org.sopt.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserJoinDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String nickname
) {
}