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

    @Query(value = "select DISTINCT season from stuff",
            nativeQuery = true)
    List<String> findUniqueSeasons();

    @Query(value = "select DISTINCT article_type from stuff order by article_type",
            nativeQuery = true)
    List<String> findArticleTypes();

    @Query(value = "select DISTINCT base_color from stuff",
            nativeQuery = true)
    List<String> findColors();

    @Query(value = "select DISTINCT master_category from stuff",
            nativeQuery = true)
    List<String> findMasterCategory();

    @Query(value = "select DISTINCT sub_category from stuff",
            nativeQuery = true)
    List<String> findSubCategory();

    // Чекнуть запрос
//    @Query(value = "select * from stuff where article_type = ?1 and base_color =?2 and gender_id = ?3 and master_category = ?4 and season = ?5 and sub_category = ?6 limit 10",
//            nativeQuery = true)
    @Query(value = "select * from stuff where (id = id or article_type = ?1) and (gender_id = ?2 and master_category != ?3 and season = ?4) limit 10",
            nativeQuery = true)
    List<Stuff> findCVStuff(String articleTypes, Long Gender, String masterCategory, String season);


}