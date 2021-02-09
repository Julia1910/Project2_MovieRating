package com.cursor.service;

import com.cursor.dao.DirectorRepository;
import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.Director;
import com.cursor.model.Movie;
import com.cursor.service.interfaces.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public DirectorDto add(DirectorDto directorDto) {
        directorRepository.save(dtoToEntity(directorDto));
        return directorDto;
    }

    @Override
    public DirectorDto getById(Long id) {
        return entityToDto(directorRepository.findById(id).orElseThrow());
    }

    @Override
    public List<DirectorDto> getAll() {
        List<DirectorDto> directorDto = new ArrayList<>();
        for (Director d: directorRepository.findAll()) {
            directorDto.add(entityToDto(d));
        }
        return directorDto;
    }

    @Override
    public void remove(Long id) {
        DirectorDto directorDto = entityToDto(directorRepository.findById(id).orElseThrow());
        directorRepository.deleteById(id);
    }

    public static Director dtoToEntity(DirectorDto directorDto) {
        Director director = new Director();
        director.setFirstName(directorDto.getFirstName());
        director.setLastName(directorDto.getLastName());
        List<Movie> movieList = new ArrayList<>();
        for (MovieDto m : directorDto.getMovies()) {
            Movie movie = new Movie();
            movie.setTitle(m.getTitle());
            movie.setRating(m.getRating());
            movie.setDescription(m.getDescription());
            movie.setCategory(m.getCategory());
            movie.setDirectors(directorDtoToDirector(m.getDirectors()));
            movieList.add(movie);
        }
        director.setMovies(movieList);
        return director;
    }

    public static List<Director> directorDtoToDirector(List<DirectorDto> directorList) {
        List<Director> directors = new ArrayList<>();
        for (DirectorDto d: directorList) {
            Director director = new Director();
            director.setFirstName(d.getFirstName());
            director.setLastName(d.getLastName());
            directors.add(director);
        }
        return directors;
    }

    public static DirectorDto entityToDto(Director director) {
        DirectorDto directorDto = new DirectorDto();
        directorDto.setFirstName(director.getFirstName());
        directorDto.setLastName(director.getLastName());
        List<MovieDto> movieList = new ArrayList<>();
        for (Movie m : director.getMovies()) {
            MovieDto movie = new MovieDto();
            movie.setTitle(m.getTitle());
            movie.setRating(m.getRating());
            movie.setDescription(m.getDescription());
            movie.setCategory(m.getCategory());
            movie.setDirectors(directorToDirectorDto(m.getDirectors()));
            movieList.add(movie);
        }
        directorDto.setMovies(movieList);
        return directorDto;
    }

    public static List<DirectorDto> directorToDirectorDto(List<Director> directorList) {
        List<DirectorDto> directorDtos = new ArrayList<>();
        for (Director d: directorList) {
            DirectorDto directorDto = new DirectorDto();
            directorDto.setFirstName(d.getFirstName());
            directorDto.setLastName(d.getLastName());
            directorDtos.add(directorDto);
        }
        return directorDtos;
    }
}
