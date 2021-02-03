package com.cursor.service.impls;

import com.cursor.dao.MovieRepository;
import com.cursor.dao.RateRepository;
import com.cursor.dto.MovieDto;
import com.cursor.model.Movie;
import com.cursor.model.enums.Category;
import com.cursor.service.interfaces.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final RateRepository rateRepository; // TODO utilize later

    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, RateRepository rateRepository, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.rateRepository = rateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MovieDto addRate(MovieDto movie, int rate) {
        return null; // TODO add logic for this method and add related method prototype to repository
    }

    @Override
    public List<MovieDto> getAllByRatingAsc() {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movieRepository.findByRatingIsNotNullOrderByRatingAsc()) {
            movieDtos.add(modelMapper.map(movie, MovieDto.class));
        }
        return movieDtos;
    }

    @Override
    public List<MovieDto> getAllByRatingDesc() {
        List<MovieDto> movieDtos = new ArrayList<>();
        for (Movie movie : movieRepository.findByRatingIsNotNullOrderByRatingDesc()) {
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
        Movie savedMovie = movieRepository.save(modelMapper.map(movieDto, Movie.class));
        return modelMapper.map(savedMovie, MovieDto.class);
    }

    @Override
    public MovieDto getById(Long id) {
        return modelMapper.map(movieRepository.findById(id), MovieDto.class);
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
    public void remove(Long id) {
        movieRepository.deleteById(id);
    }
}
