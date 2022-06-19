package com.truestyle.service;

import com.truestyle.entity.Article;
import com.truestyle.entity.Stuff;
import com.truestyle.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article findById(Long id){
        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Error, Stuff is not found"));
    }

    public String addOneArt(Article art) {
        articleRepository.save(art);
        return "ok";
    }

    public String addListArts(List<Article> arts) {
        articleRepository.saveAll(arts);
        return "ok";
    }

    public List<Article> getStuffByRecommendedThreeArt() {
        return articleRepository.findByRecommendedThreeArt();
    }

    public List<Article> getStuffByRecommendedFiveArt() {
        return articleRepository.findByRecommendedFiveArt();
    }

    public List<Article> getAllArts(){
        List<Article> target = new ArrayList<Article>();
        articleRepository.findAll().forEach(target::add);
        return target;
    }
}
