package com.truestyle.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Stuff {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String productDisplayName;

    // Гендер
    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender;

    @Column(name = "master_category", nullable = false)
    private String masterCategory;

    @Column(name = "sub_category", length = 50, nullable = false)
    private String subCategory;

    @Column(name = "article_type", length = 50, nullable = false)
    private String articleType;

    @Column(name = "base_color", length = 50, nullable = false)
    private String baseColor;

    @Column(length = 20, nullable = false)
    private String season;

    @Column(length = 20, nullable = false)
    private String usage;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

//    public Stuff(String productDisplayName, Gender gender, String masterCategory, String subCategory, String articleType, String base_color, String season, String usage, String imageUrl) {
//        this.productDisplayName = productDisplayName;
//        this.gender = gender;
//        this.masterCategory = masterCategory;
//        this.subCategory = subCategory;
//        this.articleType = articleType;
//        this.baseColor = base_color;
//        this.season = season;
//        this.usage = usage;
//        this.imageUrl = imageUrl;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Stuff stuff = (Stuff) o;
        return id != null && Objects.equals(id, stuff.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
