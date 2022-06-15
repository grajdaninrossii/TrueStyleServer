package com.truestyle.repository;

import com.truestyle.entity.Stuff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StuffRepository extends CrudRepository<Stuff, Long> {

    Optional<Stuff> findById(Long id);
    List<Stuff> findAllByOrderByIdDesc();
}