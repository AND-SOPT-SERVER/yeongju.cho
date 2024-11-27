package org.sopt.diary.domain.user.api.service;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.domain.user.core.UserRepository;
import org.sopt.diary.domain.user.User;
import org.sopt.diary.domain.user.api.dto.UserJoinDto;
import org.sopt.diary.domain.user.core.UserRetriever;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserRetriever userRetriever;

    public User join(UserJoinDto userJoinDto){
        User user = User.builder()
                .username(userJoinDto.username())
                .password(userJoinDto.password())
                .nickname(userJoinDto.nickname())
                .build();
        return userRepository.save(user);
    }

    public User findById(final Long userId){
        return userRetriever.findById(userId);
    }
}