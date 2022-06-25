package com.truestyle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.NumberFormat;

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
    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 35, message = "Name should be between 2 and 35 characters")
    @Column(nullable = false)
    private String username;

    // email пользователя
    @NotEmpty(message = "Email should not be empty")
    @Column(nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    // Ссылка на пароли
    @NotEmpty(message = "Password should not be empty")
    @Column(nullable = false)
    private String password;

    // Хранение номера телефона
    @Column(length = 4)
    private String code_number1;

    @Column(length = 4)
    private String code_number2;

    @Column(length = 7)
    private String number;

    @Column(name="full_number", length = 15)
    private String fullNumber;

    // Гендер
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    // Страна
    @Column
    @Size(min=2, max=120, message = "Country should be between 2 and 120 characters")
    private String country;

    // Фото(ссылка на фото)
    @Column(name = "photo_url")
    private String photoUrl;

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

    public void likeStuff(Stuff stuff){
        likesStuff.add(stuff);
    }

    public void dislikeStuff(Stuff stuff){
        likesStuff.remove(stuff);
    }

    public Boolean addStuff(Stuff stuff){
        return wardrobe.add(stuff);
    }

    public Boolean deleteStuff(Stuff stuff) {
        return wardrobe.remove(stuff);
    };


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