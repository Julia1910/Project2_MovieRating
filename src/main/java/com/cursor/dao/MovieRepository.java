package com.cursor.dao;

import com.cursor.model.Movie;
import com.cursor.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByRatingIsNotNullOrderByRatingAsc();

    List<Movie> findByRatingIsNotNullOrderByRatingDesc();

    List<Movie> findByCategory(Category category);
}
