package com.truestyle.controller;

import com.truestyle.entity.Stuff;
import com.truestyle.pojo.MessageResponse;
import com.truestyle.service.StuffService;
import com.truestyle.service.WardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/wardrobe")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WardrobeController {

    @Autowired
    WardrobeService wardrobeService;

    @Autowired
    StuffService stuffService;

    /** Получить одежду гардероба пользователя
     *
     * нужен токен!
     * @return если все чик пук, то вернет шмотьё, иначе пустой список
     */
    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Set<Stuff> getWardrobeBySeason(){
        return wardrobeService.getWardrobe();
    }

    /** Получить одежду гардероба пользователя по выбранному сезону
     *
     * @param season - String (Название сезона) + нужен токен!
     * @return если все чик пук, то вернет шмотьё, иначе пустой список
     * ВАЖНО: обработки на валидность сезона пока нет! Если ввести рандомное слово, то тоже вернет пустой список!!!
     */
    @GetMapping("/{season}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Stuff> getWardrobeBySeason(@PathVariable String season){
        return wardrobeService.getWardrobeBySeason(season);
    }

    /** Добавить шмотку в гардероб пользователю
     *
     * @param id_stuff - id шмотки + нужен токен!
     * @return если Алах был доволен тобой и комета минула Землю, то вернет сообщение об успешном добавлении,
     * иначе если не найдет шмотку в бд, то 500 ошибка, сервак это дело пока не обрабатывает(
     * также, если шмотка уже была добавлена, то увидишь сообщение, что мол ты ее уже добавлял
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addStuffInWardrobe(@RequestParam Long id_stuff){
        Stuff stuff = stuffService.findById(id_stuff);
        Boolean result = wardrobeService.addStuffInWardrobe(stuff);

        // Если пользователь был добавлен успешно
        if (Boolean.TRUE.equals(result)){
            return ResponseEntity.ok(new MessageResponse("Stuff ADDED"));
        }
        // Иначе
        return ResponseEntity.badRequest().body(new MessageResponse("Stuff has already been added"));
    }

    /** Удалить шмотку из гардероба пользователя
     *
     * @param id_stuff - id шмотки + нужен токен!
     * @return если черная кошка не преградила путь запросу, то вернет сообщение об успешном удалении,
     * иначе если не найдет шмотку в бд, то 500 ошибка, сервак это дело пока не обрабатывает(
     * также, если шмотка уже была удалена/не добавлялась вовсе, то увидишь сообщение, что мол незя так
     */
    @PostMapping("/delete")
    @PreAuthorize("hasRole('USER') or HasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteStuffInWardrobe(@RequestParam Long id_stuff){
        Stuff stuff = stuffService.findById(id_stuff);
        Boolean result = wardrobeService.deleteStuffInWardrobe(stuff);
        // Если пользователь был удалён успешно
        if (Boolean.TRUE.equals(result)){
            return ResponseEntity.ok(new MessageResponse("Stuff DELETED"));
        }
        // Иначе
        return ResponseEntity.ok(new MessageResponse("Stuff was not removed because it was not in your wardrobe"));
    }


}
