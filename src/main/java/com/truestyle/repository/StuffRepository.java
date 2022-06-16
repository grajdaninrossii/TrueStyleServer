package com.truestyle.repository;

import com.truestyle.entity.Stuff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StuffRepository extends CrudRepository<Stuff, Long> {

    Optional<Stuff> findById(Long id);
    List<Stuff> findAllByOrderByIdDesc();

    @Query(value = "SELECT * FROM stuff s WHERE s.season = ?1",
            nativeQuery = true)
    List<Stuff> findAllByOrderSeason(String season);

    @Query(value = "select * from stuff order by random() limit 5",
           nativeQuery = true)
    List<Stuff> findByRecommended();
}