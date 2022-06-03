package com.truestyle.entity;

import jakarta.persistence.*;
import jakarta.websocket.OnError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // Lombok
@NoArgsConstructor // Для того, чтобы Спринг смог создать бин
@AllArgsConstructor
@Table(name = "users")
public class User {

    // id пользователя
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // username имя пользователя
    @Column(name = "user_name", nullable = false)
    private String userName;

    // email пользователя
    @Column(nullable = false)
    private String email;

    // Ссылка на пароли
    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    // Хранение номера телефона
    @Column(length = 4)
    private String code_number1;

    @Column(length = 4)
    private String code_number2;

    @Column(length = 7)
    private String number;

    @Column(length = 15)
    private String full_number1;

    // Гендер
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    // Страна
    @Column
    private String country;

    // Фото(ссылка на фото)
    @Column
    private String photo_url;

    // Токен авторизации
    @OneToOne
    @JoinColumn(name = "token_id", referencedColumnName = "id")
    private Token token;
}
