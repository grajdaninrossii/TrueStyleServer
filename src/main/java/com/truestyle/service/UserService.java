package com.truestyle.service;

import com.truestyle.entity.User;
import com.truestyle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// Выделение бизнес-логики
@Service
public class UserService {

////    @Autowired
//    private UserRepository userRepository;
//
//    public UserService(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }
//
//    public void createUser(String user_name, String email, String password){
//        User newUser = new User();
//
//        newUser.setUserName(user_name);
//        newUser.setEmail(email);
//        newUser.setIdPassword(password);
//
//        userRepository.save(newUser);
//    }
//
//    /**Метод получения всех пользователей в сети*/
//    public List<User> getUsers(){
//        return userRepository.findAll();
//    }

}
