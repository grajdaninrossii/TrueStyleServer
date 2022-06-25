package com.truestyle.service;

import com.truestyle.entity.Stuff;
import com.truestyle.entity.User;
import com.truestyle.repository.RoleRepository;
import com.truestyle.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class WardrobeService {

    @Autowired
    UserRepository userRepository;

    Authentication auth;

    // Получить все шмотки пользователя (хз, зачем), треню малехо)
    public Set<Stuff> getWardrobe(){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found"));
        return user.getWardrobe();
    }

    // Выдать шмотки пользователю по определенному сезону
    public List<Stuff> getWardrobeBySeason(String season){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found"));
        ArrayList<Stuff> seasonClothes = new ArrayList<>();
        user.getWardrobe().forEach(stuff -> {
            if (stuff.getSeason().equalsIgnoreCase(season)){
                seasonClothes.add(stuff);
            }
        });
        return seasonClothes;
    }

    // Добавить шмотку пользователю
    public Boolean addStuffInWardrobe(Stuff stuff){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found, но аутентифицирован!))"));
        Boolean result = user.addStuff(stuff);
        userRepository.save(user);
        log.debug("test:" + user.toString());
//        System.out.println("test2:" + user.toString());
        return result;
    }

    // Забрать шмотку у пользователя, но не бить)
    public Boolean deleteStuffInWardrobe(Stuff stuff) {
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found, но аутентифицирован!))"));
        Boolean result = user.deleteStuff(stuff);
        userRepository.save(user);
        return result;
    }

    // Отметить шмотку как понравившуюся
    public void likeStuff(Stuff stuff){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found, но аутентифицирован!))"));
        user.likeStuff(stuff);
        userRepository.save(user);
    }

    // Убрать лайк со шмотки
    public void dislikeStuff(Stuff stuff){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found, но аутентифицирован!))"));
        user.dislikeStuff(stuff);
        userRepository.save(user);
    }

}
