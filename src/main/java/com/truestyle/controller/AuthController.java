package com.truestyle.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
import com.truestyle.service.AuthService;
import com.truestyle.service.UserDetailsImpl;
import com.truestyle.service.UserDetailsServiceImpl;
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
    AuthService authService;

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

        List<String> regRez = authService.addUser(signupRequest);
        if ("bad".equals(regRez.get(0))){
            return ResponseEntity.badRequest().body(regRez.get(1));
        }
        return ResponseEntity.ok(new MessageResponse("User CREATED"));
    }
}
