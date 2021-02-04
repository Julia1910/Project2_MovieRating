package com.cursor.service;

import com.cursor.dao.DirectorRepository;
import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.Director;
import com.cursor.model.Movie;
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
    public DirectorDto remove(Long id) {
        DirectorDto directorDto = entityToDto(directorRepository.findById(id).orElseThrow());
        directorRepository.deleteById(id);
        return directorDto;
    }

    public static Director dtoToEntity(DirectorDto directorDto) {
        Director director = new Director();
        director.setFirstName(directorDto.getFirstName());
        director.setLastName(directorDto.getLastName());
        List<Movie> movieList = new ArrayList<>();
        for (MovieDto m : directorDto.getMovie()) {
            Movie movie = new Movie();
            movie.setTitle(m.getTitle());
            movie.setRating(m.getRateValue());
            movie.setDescription(m.getShortDescription());
            movie.setCategory(m.getCategory());
            movieList.add(movie);
        }
        director.setMovie(movieList);
        return director;
    }

    public static DirectorDto entityToDto(Director director) {
        DirectorDto directorDto = new DirectorDto();
        directorDto.setFirstName(director.getFirstName());
        directorDto.setLastName(director.getLastName());
        List<MovieDto> movieList = new ArrayList<>();
        for (Movie m : director.getMovie()) {
            MovieDto movie = new MovieDto();
            movie.setTitle(m.getTitle());
            movie.setRateValue(m.getRating());
            movie.setShortDescription(m.getDescription());
            movie.setCategory(m.getCategory());
            movieList.add(movie);
        }
        directorDto.setMovie(movieList);
        return directorDto;
    }
}
