package org.sopt.diary.service.User;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.Repository.UserRepository;
import org.sopt.diary.domain.User;
import org.sopt.diary.dto.request.UserJoinDto;
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