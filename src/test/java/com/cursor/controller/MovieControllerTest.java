package com.cursor.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import com.cursor.service.MovieService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class MovieControllerTest extends BaseControllerTest {

    private MovieService movieService;
    private final MovieDto movieDto;
    private final MovieDto emptyMovieDto;

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
                Collections.singletonList(Category.COM),
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
    }

    @BeforeAll
    void setUp() {
        movieService = Mockito.mock(MovieService.class);
    }

    @Test
    void addMovieSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        MovieDto movieDtoFromResponse = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(movieDtoFromResponse, movieDto);
    }

    @Test
    void addMovieExpectNotAcceptableStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(emptyMovieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void removeMovieSuccessTest() throws Exception {
        Mockito.when(movieService.remove(1)).thenReturn(movieDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/movie/" + 1);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);
    }

    @Test
    void deleteMovieExpectNotFoundStatusTest() throws Exception {
        Mockito.when(movieService.remove(-1)).thenThrow(new Exception());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/movie/" + -1);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateInfoSuccessTest() throws Exception {
        Mockito.when(movieService.add(this.movieDto)).thenReturn(this.movieDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(this.movieDto));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);
    }

    @Test
    void updateInfoExpectNotFoundStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateInfoExpectNotAcceptableStatusTest() throws Exception {
        Mockito.when(movieService.add(emptyMovieDto)).thenThrow(new Exception());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(emptyMovieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void getByIdSuccessTest() throws Exception {
        Mockito.when(movieService.getById(1)).thenReturn(this.movieDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + 1);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(this.movieDto, movieDto);
    }

    @Test
    void getByIdExpectNotFoundStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + -1);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addRateSuccessTest() throws Exception {
        Mockito.when(
                movieService.addRate(
                        this.movieDto,
                        (int) Math.rint(this.movieDto.getRateValue()))
        ).
                thenReturn(this.movieDto);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
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

        assertEquals(this.movieDto, movieDto);
    }

    @Test
    void addRateExpectNotFoundStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addRateExpectInvalidRateStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void getAllSuccessTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

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

        assertEquals(movieDtos, movieDtosFromResponse);
    }

    @Test
    void getAllExpectNoContentStatusTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAll()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/all");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getAllByRatingAscSuccessTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

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

        assertEquals(movieDtos, movieDtosFromResponse);
    }

    @Test
    void getAllByRatingAscExpectNoContentStatusTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAllByRatingAsc()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getAllByRatingDescSuccessTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

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

        assertEquals(movieDtos, movieDtosFromResponse);
    }

    @Test
    void getAllByRatingDescExpectNoContentStatusTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAllByRatingDesc()).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating/desc");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getAllByCategorySuccessTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

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

        assertEquals(movieDtos, movieDtosFromResponse);
    }

    @Test
    void getAllByCategoryExpectNoContentStatusTest() throws Exception {
        List<MovieDto> movieDtos = new ArrayList<>();

        Mockito.when(movieService.getAllByCategory(Category.COM)).thenReturn(movieDtos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(Category.COM));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getAllByCategoryExpectNotAcceptableStatusTest() throws Exception {
        Mockito.when(movieService.getAllByCategory(null)).thenThrow(new Exception());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(null));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }
}
