package com.cursor.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

class MovieControllerIntegrationTest extends BaseControllerTest {
    private final MovieDto movieDto;
    private final MovieDto emptyMovieDto;
    private final List<MovieDto> movieDtos = new ArrayList<>();
    private static final Long idForDeleting = 17L;
    private static final Long idForGetting = 3L;
    private static final Long idForRate = 2L;


    MovieControllerIntegrationTest() {
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + idForDeleting);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDtoFromGetMethod = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        requestBuilder = MockMvcRequestBuilders.delete("/admin/movie/" + idForDeleting);

        result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDtoFromDeleteMethod = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(movieDtoFromDeleteMethod, movieDtoFromGetMethod);
    }

    @Test
    void removeMovieExpectNotFoundStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/admin/movie/" + -1);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateInfoSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + idForGetting);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDtoFromGetMethod = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        movieDtoFromGetMethod.setTitle("Title from updateInfoTest");
        movieDtoFromGetMethod.setCategory(
                Set.of(
                        Category.COM,
                        Category.ACT,
                        Category.DRAM,
                        Category.DET,
                        Category.MUS
                )
        );

        requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + idForGetting)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDtoFromGetMethod));

        result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDtoFromUpdateMethod = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertEquals(movieDtoFromUpdateMethod, movieDtoFromGetMethod);
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/movie/" + idForGetting)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(emptyMovieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void getByIdSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + idForGetting);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getByIdExpectNotFoundStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + -1);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addRateSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/" + idForRate);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDtoFromGetMethod = OBJECT_MAPPER.readValue(
                result.getResponse().getContentAsString(),
                MovieDto.class
        );

        requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + 5)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDtoFromGetMethod));

        MvcResult result1 = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto1 = OBJECT_MAPPER.readValue(
                result1.getResponse().getContentAsString(),
                MovieDto.class
        );

        requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + 6)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDtoFromGetMethod));

        MvcResult result2 = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MovieDto movieDto2 = OBJECT_MAPPER.readValue(
                result2.getResponse().getContentAsString(),
                MovieDto.class
        );

        assertNotEquals(movieDto1.getRateValue(),
                movieDto2.getRateValue());
    }

    @Test
    void addRateExpectNotFoundStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + 5)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(emptyMovieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addRateExpectNotAcceptableStatusTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movie/rate/" + -1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(movieDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void getAllSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movie/all");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getAllByRatingAscSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getAllByRatingDescSuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/rating/desc");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getAllByCategorySuccessTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stats/category")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(Category.COM));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
