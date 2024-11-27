package org.sopt.diary.domain.diary.api;

import org.sopt.diary.domain.diary.core.Diary;
import org.sopt.diary.domain.user.User;
import org.sopt.diary.domain.user.core.UserRetriever;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DiaryUserFacade {
    private final UserRetriever userRetriever;

    public DiaryUserFacade(UserRetriever userRetriever) {
        this.userRetriever = userRetriever;
    }

    public boolean isValidUser(final long userId) {

    }

    public User findUser(final long userId) {
        return userRetriever.findById(userId);
    }

    public Map<Long, String> findWriterNickname(final List<Diary> diaryList) {
        final List<Long> userIdList = diaryList.stream().map(Diary::getUserId).toList();

        return userRetriever.findNicknameMap(userIdList);
    }
}
