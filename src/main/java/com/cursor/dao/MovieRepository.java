package com.cursor.dao;

import com.cursor.model.Movie;
import com.cursor.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("update Movie m set m.rating = ?2 where m.id = ?1")
    Movie addRate(Integer rateMovieId, Double rating);

    // select m from Movie m where m.rateValue not null order by m.rateValue asc
    List<Movie> findByRatingIsNotNullOrderByRatingAsc();

    // select m from Movie m where m.rateValue not null order by m.rateValue desc
    List<Movie> findByRatingIsNotNullOrderByRatingDesc();

    // select m from Movie m where m.category = ?1
    List<Movie> findByCategory(Category category);

}
