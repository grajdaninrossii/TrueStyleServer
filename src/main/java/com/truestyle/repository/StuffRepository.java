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

    @Query(value = "select DISTINCT article_type from stuff",
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
    @Query(value = "select * from stuff s where s.article_type = ?1 and s.base_color =?2 and s.gender_id = ?3 and s.master_category = ?4 or s.sub_category = ?5",
            nativeQuery = true)
    List<Stuff> findCVStuff(String articleTypes, String baseColors, Long Gender, String masterCategory, String season, String subCategory);


}