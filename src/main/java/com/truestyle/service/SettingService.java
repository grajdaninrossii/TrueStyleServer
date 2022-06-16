package com.truestyle.service;

import com.truestyle.entity.User;
import com.truestyle.pojo.SettingRequest;
import com.truestyle.repository.GenderRepository;
import com.truestyle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SettingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GenderRepository genderRepository;

    Authentication auth;

    public List<String> saveUserSettings(SettingRequest userData){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found, но аутентифицирован!))"));
        if (!genderRepository.existsById(userData.getGender())){
            return Arrays.asList("bad", "Error: Email is exist");
        }
        user.setGender(genderRepository.findById(userData.getGender()).orElse(null));
        user.setCountry(userData.getCountry());
        user.setPhotoUrl(userData.getPhotoUrl());
        user.setFullNumber(userData.getFullNumber());
        userRepository.save(user);
        return Arrays.asList("ok", "User UPDATED");
    }
}
