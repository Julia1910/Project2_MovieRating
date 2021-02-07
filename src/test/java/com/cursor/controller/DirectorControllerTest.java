package com.cursor.controller;

import com.cursor.MovieRatingApplication;
import com.cursor.dto.DirectorDto;
import com.cursor.service.interfaces.DirectorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MovieRatingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DirectorControllerTest {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @BeforeAll
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @SneakyThrows
    @Test
    void getAll() {
        mockMvc.perform(get("/directors"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getById() {
        mockMvc.perform(get("/directors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Eugene"));


    }

    @SneakyThrows
    @Test
    void addDirector() {
        DirectorDto directorDto = new DirectorDto();
        directorDto.setFirstName("David");
        directorDto.setLastName("Cronenberg");
        directorDto.setMovies(List.of());

        mockMvc.perform( MockMvcRequestBuilders
                .post("/directors")
                .content(asJsonString(directorDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @SneakyThrows
    @Test
    void remove() {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/directors/{id}", 4))
                .andExpect(status().isOk());
    }
}