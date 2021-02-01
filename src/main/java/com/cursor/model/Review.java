package com.cursor.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String reviewMessage;
    private Boolean liked;
}