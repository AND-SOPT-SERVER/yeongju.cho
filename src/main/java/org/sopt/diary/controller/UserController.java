package org.sopt.diary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.diary.dto.request.UserJoinDto;
import org.sopt.diary.dto.response.UserResponse;
import org.sopt.diary.service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/users/register")
    public ResponseEntity<UserResponse> join(
            @RequestBody @Valid UserJoinDto userJoinDto
    ){
        return ResponseEntity.created(URI.create(
              userService.join(userJoinDto).getId().toString()
        )).build();
    }

    // 후에 인증,인가 배운 후 적용 가능
    @PostMapping("/auth/users/login")
    public ResponseEntity<Void> login(

    ){
        return null;
    }
}