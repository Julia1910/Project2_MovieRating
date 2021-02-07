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

class MovieControllerTest extends BaseControllerTest {

    @InjectMocks
    MovieController movieController;

    @Mock
    private MovieService movieService;

    private final MovieDto movieDto;
    private final MovieDto emptyMovieDto;
    private final List<MovieDto> movieDtos = new ArrayList<>();


    MovieControllerTest() {
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
                "Little kid bullies two old thives",
                5d,
                directors
        );

        emptyMovieDto = new MovieDto(
                "\t\n\t",
                null,
                "____",
                -1d,
                null
        );

        for (int i = 0; i < 5; i++) {
            movieDtos.add(movieDto);
        }
    }

    @BeforeAll
    void setUp() {
//        movieService = Mockito.mock(MovieService.class);

//        Mockito.when(movieService.add(movieDto)).thenReturn(movieDto);
//        Mockito.when(movieService.remove(1L)).thenReturn(movieDto);
    }

    @BeforeEach
    void setUpMovieService() {
//        Mockito.when(movieService.remove(1L)).thenReturn(movieDto);
        Mockito.when(movieService.add(movieDto)).thenReturn(movieDto);
//        Mockito.when(movieService.remove(1L)).thenReturn(movieDto);
        Mockito.when(movieService.getById(1L)).thenReturn(movieDto);

        Mockito.when(
                movieService.addRate(
                        this.movieDto,
                        this.movieDto.getRateValue().intValue())
        )
                .thenReturn(this.movieDto);

        Mockito.when(movieService.getAll())
                .thenReturn(new ArrayList<>())
                .thenReturn(movieDtos);

        Mockito.when(movieService.getAllByRatingAsc())
                .thenReturn(movieDtos)
                .thenReturn(new ArrayList<>());

        Mockito.when(movieService.getAllByRatingDesc())
                .thenReturn(new ArrayList<>())
                .thenReturn(movieDtos);

        Mockito.when(movieService.getAllByCategory(Category.COM))
                .thenReturn(movieDtos)
                .thenReturn(new ArrayList<>());
    }

    @Test
    void addMovieSuccessTest() {
//        Mockito.when(movieService.add(movieDto)).thenReturn(movieDto);

        ResponseEntity<MovieDto> responseEntity = movieController.addMovie(movieDto);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        MovieDto movieDtoFromResponse = responseEntity.getBody();

        assertEquals(movieDtoFromResponse, movieDto);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MovieDto movieDtoFromResponse = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(movieDtoFromResponse, movieDto);*/
    }

    @Test
    void addMovieExpectNotAcceptableStatusTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.addMovie(emptyMovieDto);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(emptyMovieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());*/
    }

    @Test
    void removeMovieSuccessTest() {
//        Mockito.when(movieService.remove(1L)).thenReturn(movieDto);

        ResponseEntity<HttpStatus> responseEntity = movieController.remove(1L);

        MovieDto movieDto = movieService.remove(1L);

        assertEquals(movieDto, responseEntity.getBody());

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

//        MovieDto movieDtoFromResponse = responseEntity.getBody();

//        assertEquals(movieDtoFromResponse, movieDto);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/movie/" + 1);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);*/
    }

    @Test
    void removeMovieExpectNotFoundStatusTest() {
//        Mockito.when(movieService.remove(-1)).thenThrow(new MovieController.UncorrectIdException(); // in UserServiceImpl should be used some custom exception

        ResponseEntity<HttpStatus> responseEntity = movieController.remove(-1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/movie/" + -1);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());*/
    }

    @Test
    void updateInfoSuccessTest() {
//        Mockito.when(movieService.add(this.movieDto)).thenReturn(this.movieDto);

        MovieDto updatedMovieDto = movieDto;

        updatedMovieDto.setCategory(
                Set.of(
                        Category.COM,
                        Category.ACT,
                        Category.DRAM
                )
        );

        Mockito.when(movieService.add(updatedMovieDto)).thenReturn(updatedMovieDto);

        ResponseEntity<MovieDto> responseEntity = movieController.updateInfo(updatedMovieDto, 1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        MovieDto movieDtoFromResponse = responseEntity.getBody();

        assertEquals(movieDtoFromResponse, updatedMovieDto);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(this.movieDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);*/
    }

    @Test
    void updateInfoExpectNotFoundStatusTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.updateInfo(movieDto, -1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());*/
    }

    @Test
    void updateInfoExpectNotAcceptableStatusTest() {

        ResponseEntity<MovieDto> responseEntity = movieController.updateInfo(emptyMovieDto, 1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE);

        /*Mockito.when(movieService.add(emptyMovieDto)).thenThrow(new Exception());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(emptyMovieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());*/
    }

    @Test
    void getByIdSuccessTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.getById(1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        MovieDto movieFromResponse = responseEntity.getBody();

        assertEquals(movieFromResponse, movieDto);

        /*Mockito.when(movieService.getById(1L)).thenReturn(this.movieDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + 1);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);*/
    }

    @Test
    void getByIdExpectNotFoundStatusTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.getById(-1L);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + -1);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());*/
    }

    @Test
    void addRateSuccessTest() {
        ResponseEntity<MovieDto> responseEntity = movieController
                .addRate(
                        movieDto,
                        this.movieDto.getRateValue().intValue()
                );

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        MovieDto movieDtoFromResponse = responseEntity.getBody();

        assertEquals(movieDtoFromResponse, movieDto);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(
                        "/movie/rate/" + (int) Math.rint(this.movieDto.getRateValue())
                )
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);*/
    }

    @Test
    void addRateExpectNotFoundStatusTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.addRate(emptyMovieDto, 10);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());*/
    }

    @Test
    void addRateExpectNotAcceptableStatusTest() {
        ResponseEntity<MovieDto> responseEntity = movieController.addRate(movieDto, -1);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE);

        /*MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());*/
    }

    @Test
    void getAllSuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAll();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            movieDtos.add(movieDto);
        }

        Mockito.when(movieService.getAll()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/all");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<MovieDto> movieDtosFromResponse = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(movieDtos, movieDtosFromResponse);*/
    }

    @Test
    void getAllExpectNoContentStatusTest() {
//        Mockito.when(movieService.getAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAll();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAll()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/all");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());*/
    }

    @Test
    void getAllByRatingAscSuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingAsc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            movieDtos.add(movieDto);
        }

        Mockito.when(movieService.getAllByRatingAsc()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<MovieDto> movieDtosFromResponse = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(movieDtos, movieDtosFromResponse);*/
    }

    @Test
    void getAllByRatingAscExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingAsc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAllByRatingAsc()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());*/
    }

    @Test
    void getAllByRatingDescSuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingDesc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            movieDtos.add(movieDto);
        }

        Mockito.when(movieService.getAllByRatingDesc()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating/desc");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<MovieDto> movieDtosFromResponse = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(movieDtos, movieDtosFromResponse);*/
    }

    @Test
    void getAllByRatingDescExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByRatingDesc();

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAllByRatingDesc()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating/desc");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());*/
    }

    @Test
    void getAllByCategorySuccessTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByCategory(Category.COM);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        List<MovieDto> movieDtosFromResponse = responseEntity.getBody();

        assertEquals(movieDtosFromResponse, movieDtos);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            movieDtos.add(movieDto);
        }

        Mockito.when(movieService.getAllByCategory(Category.COM)).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(Category.COM));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<MovieDto> movieDtosFromResponse = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );

        assertEquals(movieDtos, movieDtosFromResponse);*/
    }

    @Test
    void getAllByCategoryExpectNoContentStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByCategory(Category.COM);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        /*List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAllByCategory(Category.COM)).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(Category.COM));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());*/
    }

    /*@Test
    void getAllByCategoryExpectNotAcceptableStatusTest() {
        ResponseEntity<List<MovieDto>> responseEntity = movieController.getAllByCategory(null);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE);

        *//*Mockito.when(movieService.getAllByCategory(null)).thenThrow(new Exception());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(null));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());*//*
    }*/
}
