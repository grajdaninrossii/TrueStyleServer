package com.truestyle.repository;

import com.truestyle.entity.StyleUser;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleUserRepository extends CrudRepository<StyleUser, Long> {

    List<StyleUser> findAll();
}
