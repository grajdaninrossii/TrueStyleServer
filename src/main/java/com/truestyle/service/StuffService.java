package com.truestyle.service;


import com.truestyle.entity.Gender;
import com.truestyle.entity.Stuff;
import com.truestyle.repository.StuffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class StuffService {

//    @Autowired
    StuffRepository stuffRepository;

    public StuffService(StuffRepository stuffRepository) {this.stuffRepository = stuffRepository;}

    String addOneStuff(Stuff stuff) {
        stuffRepository.save(stuff);
        return "ok";
    }

    public String addListStuff(List<Stuff> stuffs) {
        stuffRepository.saveAll(stuffs);
        return "ok";
    }

    // На вход - id вещи
    // на выход - объект вещи
    Stuff getOneStuff(Long id) {
        final Optional<Stuff> optStuff = stuffRepository.findById(id);
        return optStuff.orElse(null);
    }

    // Получить все записи (все вещи)
    List<Stuff> getAllStuff(){
        return stuffRepository.findAllByOrderByIdDesc();
    }

    public String createAndAddStuff(String productDisplayName, Gender gender, String masterCategory,
                     String subCategory, String articleType, String base_color,
                     String season, String usage, String imageUrl) {

        Stuff oneStuff = new Stuff();
        oneStuff.setProductDisplayName(productDisplayName);
        oneStuff.setArticleType(articleType);
        oneStuff.setGender(gender);
        oneStuff.setMasterCategory(masterCategory);
        oneStuff.setBaseColor(base_color);
        oneStuff.setSubCategory(subCategory);
        oneStuff.setSeason(season);
        oneStuff.setUsage(usage);
        oneStuff.setImageUrl(imageUrl);

        try {
            stuffRepository.save(oneStuff);
        } catch (Exception e) {
            log.error("Stuff did not add", e);
        }

        return "Stuff created";
    }

}
