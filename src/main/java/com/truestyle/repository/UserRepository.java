package com.truestyle.repository;

import com.truestyle.entity.User;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "select EXISTS(select * from wardrobe where user_id = ?1 and stuff_id = ?2)",
            nativeQuery = true)
    Boolean existsStuffInWardrobe(Long userId, Long stuffId);
}