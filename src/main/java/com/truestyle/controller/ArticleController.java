package com.truestyle.controller;

import com.truestyle.entity.Article;
import com.truestyle.entity.Stuff;
import com.truestyle.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/art")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /** Получить все статьи
     *
     * @return в случае успеха вернет список статей
     */
    @GetMapping("/all")
    public List<Article> GetArticles(){
        return articleService.getAllArts();
    }

    /** Получить статью по id
     *
     * @param id - id статьи
     * @return в случае успеха вернет статью, иначе ошибка на стороне сервера
     */
    @GetMapping("{id}")
    public Article getArticleId (@PathVariable Long id){
        return articleService.findById(id);
    }

    /** Получить статьи для рекоммендаций (Слайдер с тремя статьями)
     *
     * нужен токен!
     * @return JSON(List<Stuff>)
     */
    @GetMapping("/recommended/tree")
    public List<Article> getStuffByRecommendedThreeArt(){
        return articleService.getStuffByRecommendedThreeArt();
    }

    /** Получить статьи для рекоммендаций (Слайдер на главном экране)
     *
     * нужен токен!
     * @return JSON(List<Stuff>)
     */
    @GetMapping("/recommended/five")
    public List<Article> getStuffByRecommendedFiveArt(){
        return articleService.getStuffByRecommendedFiveArt();
    }
}
