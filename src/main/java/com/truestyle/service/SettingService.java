package com.truestyle.service;

import com.truestyle.entity.Gender;
import com.truestyle.entity.Role;
import com.truestyle.entity.Stuff;
import com.truestyle.entity.User;
import com.truestyle.pojo.SettingRequest;
import com.truestyle.pojo.UserInfo;
import com.truestyle.repository.GenderRepository;
import com.truestyle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public UserInfo getUserSetting(){
        auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Error, User is not found, но аутентифицирован!))"));
        UserInfo userInfo = new UserInfo();

        userInfo.setEmail(user.getEmail());
        userInfo.setFullNumber(user.getFullNumber());
        userInfo.setGender(user.getGender());
        userInfo.setCountry(user.getCountry());
        userInfo.setPhotoUrl(user.getPhotoUrl());
        userInfo.setRoles(user.getRoles());

        return userInfo;

    }
}
