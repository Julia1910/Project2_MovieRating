package com.cursor.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Directors")
@Data
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToOne(mappedBy = "director")
    List<Movie> movieList;
}
