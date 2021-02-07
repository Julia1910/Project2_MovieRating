package com.cursor.dao;

import com.cursor.model.Director;
import com.cursor.model.Movie;
import com.cursor.model.enums.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void TestFindingById() {
        Movie m = movieRepository.findById(1L).orElseThrow();
        System.out.println(m);
    }

    @Test
    void TestFindingByTitleAndDescription() {
        String title = "Harry Potter";
        String description = "Movie about a boy, who survived";
        Movie expectedMovie = new Movie();
        expectedMovie.setId(51L);
        expectedMovie.setTitle(title);
        expectedMovie.setDescription(description);
        expectedMovie.setCategory(Set.of(Category.DRAM));
        List<Director> directors =  new ArrayList<>();
        expectedMovie.setDirectors(directors);

        Movie actualMovie = movieRepository.findByTitleAndDescription(title, description);

        assertEquals(expectedMovie, actualMovie);

    }
}