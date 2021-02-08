package com.cursor.dao;

import com.cursor.model.Movie;
import com.cursor.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByRateValueIsNotNullOrderByRateValueAsc();

    List<Movie> findByRateValueIsNotNullOrderByRateValueDesc();

    List<Movie> findByCategory(Category category);

    Movie findByTitleAndDescription(String title, String description);
}
