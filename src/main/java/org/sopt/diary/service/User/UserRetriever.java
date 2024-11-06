package org.sopt.diary.service.User;

import lombok.RequiredArgsConstructor;
import org.sopt.diary.Repository.UserRepository;
import org.sopt.diary.domain.User;
import org.sopt.diary.exception.ErrorCode;
import org.sopt.diary.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public User findById(final Long userId){
        return userRepository.findById(userId).orElseThrow(
                ()->new NotFoundException(ErrorCode.NOT_FOUND_USER)
        );
    }
}
