package com.cursor.model;

import com.cursor.model.enums.Category;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Category> category;
    private String description;

    @Formula("(select avg(r.rating) from rates r where r.movie_id = id)")
    private Double rating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Directing",
            joinColumns =
                    {@JoinColumn(name = "movie_id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "director_id")})
    private List<Director> director;
}
