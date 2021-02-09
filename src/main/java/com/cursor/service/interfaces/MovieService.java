package com.cursor.service.interfaces;

import com.cursor.dto.MovieDto;
import com.cursor.exceptions.NotFoundException;
import com.cursor.model.enums.Category;

import java.util.List;

public interface MovieService extends Service<MovieDto> {
    MovieDto addRating(MovieDto movie, int rate) throws NotFoundException;

    MovieDto addRatingById(Long movieId, int rate);

    List<MovieDto> getAllByRatingAsc();

    List<MovieDto> getAllByRatingDesc();

    List<MovieDto> getAllByCategory(Category category);

    MovieDto update(MovieDto movieDto, Long id);
}
