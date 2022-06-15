package com.truestyle.controller;

import com.truestyle.entity.Gender;
import com.truestyle.entity.Stuff;
import com.truestyle.service.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    @Autowired
    private StuffService stuffService;

    @GetMapping("/all")
    public String getAll() {
        return "public API";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String getUserApi() {
        return "User API";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String getModApi() {
        return "Moderator API";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminApi() {
        return "Moderator API";
    }

//    @RequestMapping("/kek")
//    public ModelAndView getTest(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("test");
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/kek", method = RequestMethod.GET)
//    public String getTest(){
//        return "test.html";
//    }

    @GetMapping("/add/testStuff")
    public List<Stuff> getAddedStuff(){
        List<Stuff> stuffs = new ArrayList<>();

        Gender men = new Gender(1L, "Men");
        Gender women = new Gender(2L, "Women");

        stuffs.add(new Stuff(
                15970L,
                "Turtle Check Men Navy Blue Shirt",
                men,
                "Apparel",
                "Topwear",
                "Shirts",
                "Navy Blue",
                "Fall",
                "Casual",
                "http://assets.myntassets.com/v1/images/style/properties/7a5b82d1372a7a5c6de67ae7a314fd91_images.jpg"));

        stuffs.add(new Stuff(
                39386L,
                "Peter England Men Party Blue Jeans",
                men,
                "Apparel",
                "Bottomwear",
                "Jeans",
                "Blue",
                "Summer",
                "Casual",
                "http://assets.myntassets.com/v1/images/style/properties/4850873d0c417e6480a26059f83aac29_images.jpg"));

        stuffs.add(new Stuff(
                59263L,
                "Titan Women Silver Watch",
                men,
                "Accessories",
                "Watches",
                "Watches",
                "Silver",
                "Winter",
                "Casual",
                "http://assets.myntassets.com/v1/images/style/properties/Titan-Women-Silver-Watch_b4ef04538840c0020e4"));

        stuffService.addListStuff(stuffs);

        return stuffs;
    }
}