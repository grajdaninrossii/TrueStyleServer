package com.truestyle.repository;

import com.truestyle.entity.Article;
import com.truestyle.entity.Stuff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>{

    @Query(value = "select * from article order by random() limit 3",
            nativeQuery = true)
    List<Article> findByRecommendedThreeArt();

    @Query(value = "select * from article order by random() limit 5",
            nativeQuery = true)
    List<Article> findByRecommendedFiveArt();

}
