package com.cursor.controller;

import com.cursor.MovieRatingApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MovieRatingApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DirectorControllerTest {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @Autowired
    private DirectorController directorController;

    @BeforeAll
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void addDirector() {

    }

    @Test
    void remove() {
    }
}