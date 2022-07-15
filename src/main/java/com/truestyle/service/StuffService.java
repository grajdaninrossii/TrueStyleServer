package com.truestyle.service;


import com.truestyle.entity.Gender;
import com.truestyle.entity.Stuff;
import com.truestyle.repository.GenderRepository;
import com.truestyle.repository.StuffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class StuffService {

    @Autowired
    StuffRepository stuffRepository;

    @Autowired
    GenderRepository genderRepository;

    List<String> articleTypes;
    List<String> season;
    List<String> subCategory;
    List<String> baseColors;
    List<String> masterCategory;
    List<Gender> gender;
    Long gnId; // Костыль

    private void fillUniqueData(){
        assert stuffRepository != null;
        articleTypes = stuffRepository.findArticleTypes();
        season = Arrays.asList("Fall", "Summer", "Winter", "Spring", "NaN");
        subCategory = stuffRepository.findSubCategory();
        baseColors = stuffRepository.findColors();
        masterCategory = Arrays.asList("Apparel", "Accessories", "Footwear", "Personal Care", "Free Items",
                "Sporting Goods", "Home");;
        gender = (List<Gender>) genderRepository.findAll();
    }

    public Stuff findById(Long id){
        return stuffRepository.findById(id).orElseThrow(() -> new RuntimeException("Error, Stuff is not found"));
    }

    // Получение характеристик шмоток из фотки
    private List<String> getStuffCharacters(List<Integer> data){
        if (articleTypes == null){
            fillUniqueData();
        }
        gnId = gender.get(data.get(2)).getId();
        List<String> dataClasses = Arrays.asList(articleTypes.get(data.get(0)),
                baseColors.get(data.get(1)),
                masterCategory.get(data.get(3)),
                season.get(data.get(4)),
                subCategory.get(data.get(5)));
        return dataClasses;
    }

    // Получение шмоток по характеристикам
    public List<Stuff> getStuffML(List<Integer> data){
        System.out.println(data.toString());
        List<String> characters = getStuffCharacters(data);
        System.out.println(characters.toString());
//        System.out.println(this.articleTypes.toString());
        return stuffRepository.findCVStuff(characters.get(0), gnId,
                characters.get(2), characters.get(3));
    }

    public String addOneStuff(Stuff stuff) {
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

    public List<Stuff> getStuffBySeason(String season){
        return stuffRepository.findAllByOrderSeason(season);
    }

    public List<Stuff> getStuffByRecommended() {
        return stuffRepository.findByRecommended();
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
