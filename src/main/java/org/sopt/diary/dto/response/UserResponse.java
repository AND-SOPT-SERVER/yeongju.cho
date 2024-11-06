package org.sopt.diary.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserResponse(
        @NotBlank(message = "Username 은 필수로 입력해야 합니다.")
        String username,
        @NotBlank(message = "Nickname 은 필수로 입력해야 합니다.")
        String nickname
) {
}