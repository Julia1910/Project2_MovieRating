package com.cursor.controller;

import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import com.cursor.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping(
            value = "/admin/movie",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> addMovie(MovieDto movieDto) {
        return null;
    }

    @DeleteMapping(
            value = "/admin/movie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> remove(@PathVariable String id) {
        return null;
    }

    @PostMapping(
            value = "/admin/movie/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> updateInfo(
            @RequestBody MovieDto movieDto,
            @PathVariable int id) {
        return null;
    }

    @GetMapping(
            value = "/movie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> getById(@PathVariable int id) {
        return null;
    }

    @PostMapping(
            value = "/movie/rate/{rate}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> addRate(
            @RequestBody MovieDto movieDto,
            @PathVariable int rate) {
        return null;
    }

    @GetMapping(
            value = "/movie/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAll() {
        return null;
    }

    @GetMapping(
            value = "/stats/rating",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAllByRatingAsc() {
        return null;
    }

    @GetMapping(
            value = "/stats/rating/desc",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAllByRatingDesc() {
        return null;
    }

    @GetMapping(
            value = "/stats/category",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<MovieDto>> getAllByCategory(@RequestBody Category category) {
        return null;
    }
}
