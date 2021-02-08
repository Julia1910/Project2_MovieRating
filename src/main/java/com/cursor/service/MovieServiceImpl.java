package com.cursor.service;

import com.cursor.dao.MovieRepository;
import com.cursor.dao.RateRepository;
import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.exceptions.NotFoundException;
import com.cursor.exceptions.IncorrectMovieDtoException;
import com.cursor.exceptions.IncorrectRateValueException;
import com.cursor.model.Director;
import com.cursor.model.Movie;
import com.cursor.model.Rate;
import com.cursor.model.enums.Category;
import com.cursor.service.interfaces.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final RateRepository rateRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, RateRepository rateRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.rateRepository = rateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MovieDto addRateValue(MovieDto movie, int rateValue) {
        try {
            checkRateValue(rateValue);
        } catch (IncorrectRateValueException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    exception.getMessage(),
                    exception
            );
        }

        String title = movie.getTitle();
        String description = movie.getDescription();
        Movie movie1 = movieRepository.findByTitleAndDescription(title, description);

        if (movie1 == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "The movie was not found"
            );
        }

        Long movieId = movie1.getId();
        Rate movieRate = new Rate();
        movieRate.setMovie(movie1);
        movieRate.setRating(rateValue);
        rateRepository.save(movieRate);
        return getById(movieId);
    }

    @Override
    public MovieDto addRateValueById(Long movieId, int rate) {
        MovieDto movieDto = getById(movieId);
        movieDto = addRateValue(movieDto, rate);
        return movieDto;
    }

    @Override
    public List<MovieDto> getAllByRatingAsc() {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movieRepository.findByRateValueIsNotNullOrderByRateValueAsc()) {
            movieDtos.add(modelMapper.map(movie, MovieDto.class));
        }
        return movieDtos;
    }

    @Override
    public List<MovieDto> getAllByRatingDesc() {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movieRepository.findByRateValueIsNotNullOrderByRateValueDesc()) {
            movieDtos.add(modelMapper.map(movie, MovieDto.class));
        }
        return movieDtos;
    }

    @Override
    public List<MovieDto> getAllByCategory(Category category) {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movieRepository.findByCategory(category)) {
            movieDtos.add(modelMapper.map(movie, MovieDto.class));
        }
        return movieDtos;
    }

    @Override
    public MovieDto add(MovieDto movieDto) {
        try {
            checkMovieDto(movieDto);
        } catch (IncorrectMovieDtoException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    exception.getMessage(),
                    exception
            );
        }

        Movie savedMovie = movieRepository.save(modelMapper.map(movieDto, Movie.class));
        return modelMapper.map(savedMovie, MovieDto.class);
    }

    @Override
    public MovieDto getById(Long id) {
        return movieRepository.findById(id).
                map(movie -> modelMapper.map(movie, MovieDto.class))
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<MovieDto> getAll() {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movieRepository.findAll()) {
            MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
            movieDtos.add(movieDto);
        }
        return movieDtos;
    }

    @Override
    public MovieDto remove(Long id) {
        MovieDto movieDto = getById(id);
        movieRepository.deleteById(id);
        return movieDto;
    }

    @Override
    public MovieDto update(MovieDto movieDto, Long id) {
        Movie movieFromDatabase = movieRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        try {
            checkMovieDto(movieDto);
        } catch (IncorrectMovieDtoException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_ACCEPTABLE,
                    exception.getMessage(),
                    exception
            );
        }

        List<Director> directors = new ArrayList<>();

        for (DirectorDto directorDto : movieDto.getDirectors()) {
            directors.add(modelMapper.map(directorDto, Director.class));
        }

        movieFromDatabase.setTitle(movieDto.getTitle());
        movieFromDatabase.setCategory(movieDto.getCategory());
        movieFromDatabase.setDescription(movieDto.getDescription());
        movieFromDatabase.setRateValue(movieDto.getRateValue());
        movieFromDatabase.setDirectors(directors);

        movieRepository.save(movieFromDatabase);

        return movieDto;
    }

    private void checkMovieDto(MovieDto movieDto) throws IncorrectMovieDtoException {
        if (movieDto.getTitle().isBlank() || movieDto.getCategory().isEmpty()
                || movieDto.getDescription().isBlank())
            throw new IncorrectMovieDtoException("MovieDto's details are incorrect");
    }

    private void checkRateValue(int rateValue) throws IncorrectRateValueException {
        if (rateValue < 0 || rateValue > 10)
            throw new IncorrectRateValueException("The rate value is incorrect");
    }
}
