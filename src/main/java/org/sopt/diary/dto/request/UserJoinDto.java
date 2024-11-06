package org.sopt.diary.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserJoinDto(
        @NotBlank(message = "Username 은 필수로 입력해야 합니다.")
        String username,
        @NotBlank(message = "Password 는 필수로 입력해야 합니다.")
        String password,
        @NotBlank(message = "Nickname 은 필수로 입력해야 합니다.")
        String nickname
) {
}