package com.cursor.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.enums.Category;
import org.junit.jupiter.api.Test;
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

    @Test
    void addMovieSuccessTest() throws Exception {

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

        MovieDto movieDto = new MovieDto(
                "Home alone",
                Collections.singletonList(Category.COM),
                "Little kid bullies two old thives",
                10d,
                directors
        );

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
}
