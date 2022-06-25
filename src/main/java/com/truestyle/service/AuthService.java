package com.truestyle.service;

import com.truestyle.config.jwt.JwtUtils;
import com.truestyle.entity.ERole;
import com.truestyle.entity.PasswordResetToken;
import com.truestyle.entity.Role;
import com.truestyle.entity.User;
import com.truestyle.pojo.JwtResponse;
import com.truestyle.pojo.LoginRequest;
import com.truestyle.pojo.SignupRequest;
import com.truestyle.repository.PasswordTokenRepository;
import com.truestyle.repository.RoleRepository;
import com.truestyle.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    public JwtResponse getUserData(LoginRequest loginRequest) {

        // Менеджер аутентификации, передаем в конструктор токен аутентификации, в котором имя пользователя и пароль
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        // Устанавливаем аутентификацию
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication); // генерируем токен

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

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

//        System.out.println(reqRoles);

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

    public List<String> checkUsername(String username){
        if (userRepository.existsByUsername(username)){
            return Arrays.asList("bad", "Error: Username is exist");
        }
        return Arrays.asList("good", "Username isn't exist yet");
    }

    public List<String> checkEmail(String email){
        if (userRepository.existsByEmail(email)){
            return Arrays.asList("bad", "Error: Email is exist");
        }
        return Arrays.asList("good", "Email isn't exist yet");
    }

}
