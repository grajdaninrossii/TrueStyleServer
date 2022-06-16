package com.truestyle.repository;

import com.truestyle.entity.Gender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderRepository extends CrudRepository<Gender, Long> {
}
