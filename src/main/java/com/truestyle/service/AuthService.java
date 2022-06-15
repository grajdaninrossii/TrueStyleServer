package com.truestyle.service;

import com.truestyle.entity.ERole;
import com.truestyle.entity.Role;
import com.truestyle.entity.User;
import com.truestyle.pojo.SignupRequest;
import com.truestyle.repository.RoleRepository;
import com.truestyle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<String> addUser(SignupRequest signupRequest){
        // Если пользователь есть в базе, то возвращаем сообщение об ошибке
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return Arrays.asList("bad", "Error: Username is exist");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return Arrays.asList("bad", "Error: Email is exist");
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        System.out.println(reqRoles);

        if (reqRoles == null) {
            Role userRole = roleRepository
                    .findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
            roles.add(userRole);
        } else {
            reqRoles.forEach(r -> {
                switch (r) {
                    case "admin" -> {
                        Role adminRole = roleRepository
                                .findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error, Role ADMIN is not found"));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository
                                .findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error, Role MODERATOR is not found"));
                        roles.add(modRole);
                    }

                    // По дефолту добавляем роль User
                    default -> {
                        Role userRole = roleRepository
                                .findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error, Role USER is not found"));
                        roles.add(userRole);
                    }
                }
            });
        }
        // Устанавливаем роли нашему пользователю (код сверху это жесть, нужно переписать)
        user.setRoles(roles);
        userRepository.save(user); // сохраняем пользователя в бд
        return Arrays.asList("good", "User CREATED");
    }
}
