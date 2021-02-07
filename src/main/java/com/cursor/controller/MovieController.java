package com.cursor.controller;

import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import com.cursor.service.interfaces.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(
            value = "/admin/movie",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movieDto) {
        MovieDto movieDtoFromService = movieService.add(movieDto);
        return new ResponseEntity<>(movieDtoFromService, HttpStatus.CREATED);
    }

    @DeleteMapping(
            value = "/admin/movie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> remove(@PathVariable Long id) {
        MovieDto movieDtoFromService = movieService.remove(id);
        return new ResponseEntity<>(movieDtoFromService, HttpStatus.OK);
    }

    @PostMapping(
            value = "/admin/movie/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> updateInfo(
            @RequestBody MovieDto movieDto,
            @PathVariable Long id) {
        MovieDto movieDtoFromService = movieService.update(movieDto, id);
        return new ResponseEntity<>(movieDtoFromService, HttpStatus.OK);
    }

    @GetMapping(
            value = "/movie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> getById(@PathVariable Long id) {
        MovieDto movieDtoFromService = movieService.getById(id);
        return new ResponseEntity<>(movieDtoFromService, HttpStatus.OK);
    }

    @PostMapping(
            value = "/movie/rate/{rate}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> addRate(
            @RequestBody MovieDto movieDto,
            @PathVariable int rate) {
        MovieDto movieDtoFromController = movieService.addRate(movieDto, rate);
        return new ResponseEntity<>(movieDtoFromController, HttpStatus.OK);
    }

    @GetMapping(
            value = "/movie/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAll() {
        List<MovieDto> movieDtosFromService = movieService.getAll();

        if (movieDtosFromService.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(movieDtosFromService, HttpStatus.OK);
    }

    @GetMapping(
            value = "/stats/rating",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAllByRatingAsc() {
        List<MovieDto> movieDtosFromService = movieService.getAllByRatingAsc();

        if (movieDtosFromService.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(movieDtosFromService, HttpStatus.OK);
    }

    @GetMapping(
            value = "/stats/rating/desc",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAllByRatingDesc() {
        List<MovieDto> movieDtosFromService = movieService.getAllByRatingDesc();

        if (movieDtosFromService.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(movieDtosFromService, HttpStatus.OK);
    }

    @GetMapping(
            value = "/stats/category",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAllByCategory(@RequestBody Category category) {
        List<MovieDto> movieDtosFromService = movieService.getAllByCategory(category);

        if (movieDtosFromService.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(movieDtosFromService, HttpStatus.OK);
    }
}
