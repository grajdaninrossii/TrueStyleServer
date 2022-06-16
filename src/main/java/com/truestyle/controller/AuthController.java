package com.truestyle.controller;

import java.util.List;

import com.truestyle.pojo.*;
import com.truestyle.service.AuthService;
import com.truestyle.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600) // Работа с безопасностью браузера
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    SettingService settingService;

    /** Аутентификация пользователя
     *
     * @param loginRequest:JSON(Объект) вида {"user_name": String,
     *                                          "password": String}
     * @return - JSON вида {
     *     "token": String,
     *     "type": "Bearer",
     *     "id": Integer,
     *     "username": String,
     *     "email": String,
     *     "roles": List<Role>
     * }
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse userData = authService.getUserData(loginRequest);
        return ResponseEntity.ok(userData);
    }


    /** Регистрация пользователя
     *
     * @param signupRequest - JSON(Объект) вида {
     *     "username": String,
     *     "email": String,
     *     "password": String,
     *     "roles": List<Role> (в виде json, например, список из всех доступных ролей: ["admin", "mod", "user"])
     * }
     * @return в случае успеха вернет "User CREATED" иначе 4** ошибку
     */
    // Создание пользователя
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        List<String> result = authService.addUser(signupRequest);
        MessageResponse message = new MessageResponse(result.get(1));
        if ("bad".equals(result.get(0))){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    /** Проверка на наличие пользователя в бд по username
     *
     * @param username - имя пользователя для проверки
     * @return в случае успеха вернет сообщение о том, что все гуд
     * иначе сообщение об ошибке
     */
    @GetMapping("check/username")
    public ResponseEntity<?> checkUsername(@RequestParam String username){
        List<String> result = authService.checkUsername(username);
        MessageResponse message = new MessageResponse(result.get(1));
        if ("bad".equals(result.get(0))){
         return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    /** Проверка на наличие пользователя в бд по email
     *
     * @param username - имя пользователя для проверки
     * @return в случае успеха вернет сообщение о том, что все гуд
     * иначе сообщение об ошибке
     */
    @GetMapping("check/email")
    public ResponseEntity<?> checkEmail(@RequestParam String username){
        List<String> result = authService.checkEmail(username);
        MessageResponse message = new MessageResponse(result.get(1));
        if ("bad".equals(result.get(0))){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    /** Обновление полей пользователя(без email, password, username)
     *
     * @param settingRequest - JSON(Объект SettingRequest) вида {
     *     "fullNumber": "8**********",
     *     "gender": 1,
     *     "country": "Russia",
     *     "photoUrl": "heh"
     * }
     * @return - вернет сообщение об успешном добавление
     * иначе сообщение об ошибке
     */
    @PostMapping("/setting")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> setDetailsUser(@RequestBody SettingRequest settingRequest){
        List<String> result = settingService.saveUserSettings(settingRequest);
        MessageResponse message = new MessageResponse(result.get(1));
        if ("bad".equals(result.get(0))){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

//    @PostMapping("/setting/password")
//    public ResponseEntity<?> setNewPassword(@RequestBody LoginRequest user){
//
//    }
}
