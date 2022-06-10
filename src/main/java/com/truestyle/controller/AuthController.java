package com.truestyle.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.truestyle.config.jwt.JwtUtils;
import com.truestyle.entity.ERole;
import com.truestyle.entity.Role;
import com.truestyle.entity.User;
import com.truestyle.pojo.JwtResponse;
import com.truestyle.pojo.LoginRequest;
import com.truestyle.pojo.MessageResponse;
import com.truestyle.pojo.SignupRequest;
import com.truestyle.repository.RoleRepository;
import com.truestyle.repository.UserRepository;
import com.truestyle.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600) // Работа с безопасностью браузера
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

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

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    // Создание пользователя
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        // Если пользователь есть в базе, то возвращаем сообщение об ошибке
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> reqRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

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
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }
}
