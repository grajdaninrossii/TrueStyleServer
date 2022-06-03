package com.truestyle.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String value;

    @Column(nullable = false)
    private Date to_Date;

    // Делаем двусторонее отношение, где mappedBy указвает владельца отношения
    @OneToOne(mappedBy = "token")
    private User user;
}