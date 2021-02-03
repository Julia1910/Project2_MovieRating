package com.cursor.service;

import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;

import java.util.List;

public interface MovieService extends Service<MovieDto> {
    MovieDto addRate(MovieDto movie, int rate);

    List<MovieDto> getAllByRatingAsc();

    List<MovieDto> getAllByRatingDesc();

    List<MovieDto> getAllByCategory(Category category);
}
