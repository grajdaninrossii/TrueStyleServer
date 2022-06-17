package com.truestyle.controller;


import com.truestyle.pojo.MessageResponse;
import com.truestyle.pojo.NewPasswordRequest;
import com.truestyle.pojo.SettingRequest;
import com.truestyle.service.SecurityService;
import com.truestyle.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600) // Работа с безопасностью браузера
public class UserController {


    @Autowired
    SettingService settingService;

    @Autowired
    SecurityService securityService;

    /** Обновление полей пользователя(без email, password, username)
     *
     * @param settingRequest - JSON(Объект SettingRequest) вида {
     *     "fullNumber": "8**********",
     *     "gender": Interger(id гендера),
     *     "country": String,
     *     "photoUrl": String
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

    /** Запрос на смену пароля
     *
     * @param userEmail - почта пользователя
     * @return все отлично, если найдет почту и отправит на нее письмо с токеном
     * иначе, скажет что не нашел почту
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail) {

        List<String> result = securityService.resetPassword(userEmail);
        MessageResponse message = new MessageResponse(result.get(1));
        if ("bad".equals(result.get(0))){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    /** Проверка валидности токена
     *
     * @param token - токен из письма
     * @return вернет сообщение с результатом проверки токена
     */
    @GetMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam("token") String token) {

        List<String> result = securityService.isCorrectTokenForChangePassword(token);
        MessageResponse message = new MessageResponse(result.get(1));
        if (!"good".equals(result.get(0))){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    /** Установка нового пароля
     *
     * @param newPassword - JSON(Объект NewPasswordRequest) вида {
     *     "token":String,
     *     "password": String
     * }
     * @return - вернет сообщение об успешном обновлении,
     * иначе может поругаться на токен
     */
    @PostMapping("savePassword")
    public ResponseEntity<?> saveNewPassword(@RequestBody NewPasswordRequest newPassword){
        List<String> result = securityService.changePassword(newPassword.getPassword(), newPassword.getToken());
        MessageResponse message = new MessageResponse(result.get(1));
        if (!"good".equals(result.get(0))){
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }
}
