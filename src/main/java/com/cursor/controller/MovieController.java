package com.cursor.controller;

import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import com.cursor.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    private final MovieService movieService;
    private boolean checkMovieDtoFlag = true;
    private boolean checkIdFlag = true;
    private boolean checkRateFlag = true;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    private void checkMovieDto(MovieDto movieDto) throws IncorrectMovieDtoException {
        if (movieDto.getTitle().isBlank() || movieDto.getCategories().isEmpty()
                || movieDto.getShortDescription().isBlank())
            throw new IncorrectMovieDtoException("MovieDto's details are incorrect");
    }

    private void checkId(Long id) throws IncorrectIdException {
        if (id < 0)
            throw new IncorrectIdException("The object with id " + id + " doesn't exist");
    }

    private void checkRate(int rate) throws IncorrectRateException {
        if (rate < 0 || rate > 10)
            throw new IncorrectRateException("The rate value is incorrect");
    }

    @PostMapping(
            value = "/admin/movie",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movieDto) {
        if (checkMovieDtoFlag) {
            try {
                checkMovieDto(movieDto);
            } catch (IncorrectMovieDtoException e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        MovieDto movieDtoFromService = movieService.add(movieDto);
        return new ResponseEntity<>(movieDtoFromService, HttpStatus.CREATED);
    }

    @DeleteMapping(
            value = "/admin/movie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> remove(@PathVariable Long id) {
        if (checkIdFlag) {
            try {
                checkId(id);
            } catch (IncorrectIdException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        MovieDto movieDto = movieService.remove(id);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @PostMapping(
            value = "/admin/movie/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> updateInfo(
            @RequestBody MovieDto movieDto,
            @PathVariable Long id) {

        if (checkIdFlag) {
            try {
                checkId(id);
            } catch (IncorrectIdException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        if (checkMovieDtoFlag) {
            try {
                checkMovieDto(movieDto);
            } catch (IncorrectMovieDtoException e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        MovieDto movieDtoFromService = movieService.add(movieDto);
        return new ResponseEntity<>(movieDtoFromService, HttpStatus.OK);
    }

    @GetMapping(
            value = "/movie/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MovieDto> getById(@PathVariable Long id) {
        if (checkIdFlag) {
            try {
                checkId(id);
            } catch (IncorrectIdException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

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
        if (checkMovieDtoFlag) {
            try {
                checkMovieDto(movieDto);
            } catch (IncorrectMovieDtoException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        if (checkRateFlag) {
            try {
                checkRate(rate);
            } catch (IncorrectRateException e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

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

    class IncorrectMovieDtoException extends Throwable {
        private String message;

        public IncorrectMovieDtoException(String message) {
            super(message);
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    class IncorrectIdException extends Throwable {
        public IncorrectIdException(String message) {
            super(message);
        }
    }

    class IncorrectRateException extends Throwable {
        public IncorrectRateException(String message) {
            super(message);
        }
    }
}
