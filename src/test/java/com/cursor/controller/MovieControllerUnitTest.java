package com.cursor.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import com.cursor.service.interfaces.MovieService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

class MovieControllerUnitTest extends BaseControllerTest {

    @InjectMocks
    MovieController movieController;

    @Mock
    private MovieService movieServiceMock;

    private final MovieDto movieDto;
    private final List<MovieDto> movieDtos = new ArrayList<>();


    MovieControllerUnitTest() {
        List<DirectorDto> directors = new ArrayList<>(Arrays.asList(
                new DirectorDto(
                        "Raja",
                        "Gosnell",
                        null
                ),
                new DirectorDto(
                        "Peter",
                        "Hewitt",
                        null
                ),
                new DirectorDto(
                        "Chris",
                        "Columbus",
                        null
                ),
                new DirectorDto(
                        "Rod",
                        "Daniel",
                        null
                )
        ));

        movieDto = new MovieDto(
                "Home alone",
                Set.of(Category.COM),
                "Little kid bullies two old thieves",
                5d,
                directors
        );

        for (int i = 0; i < 5; i++) {
            movieDtos.add(movieDto);
        }
    }

    @BeforeEach
    void setUpMovieService() {
        Mockito.when(movieServiceMock.add(movieDto)).thenReturn(movieDto);
        Mockito.when(movieServiceMock.getById(1L)).thenReturn(movieDto);

        Mockito.when(
                movieServiceMock.addRating(
                        this.movieDto,
                        this.movieDto.getRating().intValue())
        )
                .thenReturn(this.movieDto);

        Mockito.when(movieServiceMock.getAll())
                .thenReturn(new ArrayList<>())
                .thenReturn(movieDtos);

        Mockito.when(movieServiceMock.getAllByRatingAsc())
                .thenReturn(movieDtos)
                .thenReturn(new ArrayList<>());

        Mockito.when(movieServiceMock.getAllByRatingDesc())
                .thenReturn(new ArrayList<>())
                .thenReturn(movieDtos);

        Mockito.when(movieServiceMock.getAllByCategory(Category.COM))
                .thenReturn(movieDtos)
                .thenReturn(new ArrayList<>());
    }

    @Test
    void addMovieSuccessTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.addMovie(movieDto);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        MovieDto movieDtoFromResponse = responseEntity.getBody();

        assertEquals(movieDtoFromResponse, movieDto);
    }

    @Test
    void removeMovieSuccessTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.remove(1L);

        MovieDto movieDto = responseEntity.getBody();

        assertEquals(movieDto, responseEntity.getBody());

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateInfoSuccessTest() {
        MovieDto updatedMovieDto = movieDto;

        updatedMovieDto.setCategory(
                Set.of(
                        Category.COM,
                        Category.ACT,
                        Category.DRAM
                )
        );

        Mockito.when(movieServiceMock.update(updatedMovieDto, 1L)).thenReturn(updatedMovieDto);

        ResponseEntity<MovieDto> responseEntity = movieController.updateInfo(updatedMovieDto, 1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        MovieDto movieDtoFromResponse = responseEntity.getBody();

        assertEquals(movieDtoFromResponse, updatedMovieDto);
    }

    @Test
    void getByIdSuccessTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.getById(1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        MovieDto movieFromResponse = responseEntity.getBody();

        assertEquals(movieFromResponse, movieDto);
    }

    @Test
    void addRateSuccessTest() {
        ResponseEntity<MovieDto> responseEntity = movieController
                .addRate(
                        movieDto,
                        this.movieDto.getRating().intValue()
                );

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        MovieDto movieDtoFromResponse = responseEntity.getBody();

        assertEquals(movieDtoFromResponse, movieDto);
    }

    @Test
    void getAllSuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAll();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);
    }

    @Test
    void getAllExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAll();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllByRatingAscSuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingAsc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);
    }

    @Test
    void getAllByRatingAscExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingAsc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllByRatingDescSuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingDesc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);
    }

    @Test
    void getAllByRatingDescExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingDesc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getAllByCategorySuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByCategory(Category.COM);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);
    }

    @Test
    void getAllByCategoryExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByCategory(Category.COM);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }
}
