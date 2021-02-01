package com.cursor.model;

import com.cursor.model.enums.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Category> category = new ArrayList<>();

    private String description;
    private Double rating;

    @ManyToMany
    @JoinTable(name = "directing",
            joinColumns =
                    {@JoinColumn(name = "movie")},
            inverseJoinColumns =
                    {@JoinColumn(name = "director")})
    private List<Director> director;
}
