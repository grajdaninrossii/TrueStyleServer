package com.truestyle.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Proxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Proxy(lazy = false)
@Table(name = "gender",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "gender_name")
})
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 20, nullable = false)
    private String gender_name;

//    @ManyToOne(optional = false)
//    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Gender gender = (Gender) o;
        return id != null && Objects.equals(id, gender.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
