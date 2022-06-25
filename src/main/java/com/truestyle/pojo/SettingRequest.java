package com.truestyle.pojo;

import com.truestyle.entity.Gender;
import com.truestyle.entity.Role;
import com.truestyle.entity.Stuff;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SettingRequest {

    // Хранение номера телефона
    private String fullNumber;

    // Гендер
    private Long gender;

    // Страна
    private String country;

    // Фото(ссылка на фото)
    private String photoUrl;
}
