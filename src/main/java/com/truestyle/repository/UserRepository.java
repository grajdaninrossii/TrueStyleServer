package com.truestyle.repository;

import com.truestyle.entity.User;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Table(name = "users")
public interface UserRepository extends CrudRepository<User, Long> {
//
//    User findByUserId(Integer id);
//
//    User findByUserName(String user_name);
//
//    List<User> findAll();
}