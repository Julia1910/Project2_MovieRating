package com.cursor.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Rates")
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Integer rating;
}
