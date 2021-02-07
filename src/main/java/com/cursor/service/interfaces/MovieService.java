package com.cursor.service.interfaces;

import com.cursor.dto.MovieDto;
import com.cursor.exceptions.NotFoundException;
import com.cursor.model.Movie;
import com.cursor.model.enums.Category;

import java.util.List;

public interface MovieService extends Service<MovieDto> {
    MovieDto addRate(MovieDto movie, int rate) throws NotFoundException;

    List<MovieDto> getAllByRatingAsc();

    List<MovieDto> getAllByRatingDesc();

    List<MovieDto> getAllByCategory(Category category);

    MovieDto update(MovieDto movieDto, Long id);
}
