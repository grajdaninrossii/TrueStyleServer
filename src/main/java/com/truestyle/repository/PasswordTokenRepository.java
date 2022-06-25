package com.truestyle.repository;

import com.truestyle.entity.PasswordResetToken;
import com.truestyle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    void deleteByToken(String token);

    boolean existsByToken(String token);
}
