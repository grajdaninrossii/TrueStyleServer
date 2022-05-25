package com.truestyle.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "secret_word_category")
    private String secretWordCategory;

    @Column(name = "secret_word")
    private String secretWord;

    @OneToOne(mappedBy = "password")
    private User User;
}
