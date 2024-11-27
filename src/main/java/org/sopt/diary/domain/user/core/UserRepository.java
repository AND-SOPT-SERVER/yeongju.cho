package org.sopt.diary.domain.user.core;

import org.sopt.diary.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(final Long userId);
}