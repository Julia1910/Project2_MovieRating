package com.cursor.model;

import com.cursor.model.enums.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    private Integer id;

    private String title;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Category> category = new ArrayList<>();

    private String description;
    private Double rating;

    @ManyToMany
    @JoinTable(name = "directing",
            joinColumns =
                    {@JoinColumn(name = "id(movie)")},
            inverseJoinColumns =
                    {@JoinColumn(name = "id(Director)")})
    private List<Director> director;
}
