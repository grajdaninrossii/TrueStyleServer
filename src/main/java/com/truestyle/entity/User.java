package com.truestyle.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor // Для того, чтобы Спринг смог создать бин
@AllArgsConstructor
@Table(name = "users",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
               @UniqueConstraint(columnNames = "email")
       })
public class User {

    // id пользователя
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // username имя пользователя
    @Column(nullable = false)
    private String username;

    // email пользователя
    @Column(nullable = false)
    private String email;

    // Ссылка на пароли
    @Column(nullable = false)
    private String password;

    // Хранение номера телефона
    @Column(length = 4)
    private String code_number1;

    @Column(length = 4)
    private String code_number2;

    @Column(length = 7)
    private String number;

    @Column(length = 15)
    private String full_number;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "likes_stuff",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "stuff_id"))
    private Set<Stuff> likesStuff = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "wardrobe",
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "stuff_id"))
    private Set<Stuff> wardrobe = new HashSet<>();

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }


//    // Токен авторизации
//    @OneToOne
//    @JoinColumn(name = "token_id", referencedColumnName = "id")
//    private Token token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
//
//@Entity
//@Data
//@NoArgsConstructor
//@Table(name = "users",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = "username"),
//                @UniqueConstraint(columnNames = "email")
//        })
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String username;
//    private String email;
//    private String password;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();
//
//    public User(String username, String email, String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
//}
