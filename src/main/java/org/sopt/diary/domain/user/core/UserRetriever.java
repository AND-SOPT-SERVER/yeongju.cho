package org.sopt.diary.domain.user.core;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.domain.user.User;
import org.sopt.diary.domain.user.core.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public UserRetriever(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findById(final long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Map<Long, String> findNicknameMap(final List<Long> userIdList) {
        final List<User> userList = userRepository.findAllById(userIdList);

        return userList.stream()
                .collect(
                        Collectors.toMap(
                                // key
                                User::getId,
                                // value
                                User::getNickname
                        )
                );
    }
}
