package com.cursor.service;

import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.dto.ReviewDto;
import com.cursor.model.enums.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    void add() {
        ReviewDto reviewDto = new ReviewDto();
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("American Pie");
        movieDto.setShortDescription("Stupid movie");
        List<Category> categories = new ArrayList<>();
        categories.add(Category.COM);
        movieDto.setCategory(categories);
        List<DirectorDto> directorDtos = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto();
        directorDto.setFirstName("Eugene");
        directorDto.setLastName("Levy");
        directorDtos.add(directorDto);
        movieDto.setDirectors(directorDtos);
        reviewDto.setLiked(true);
        reviewDto.setMovie(movieDto);
        reviewDto.setReviewMessage("Some review message");
        ReviewDto reviewDto1 = reviewService.add(reviewDto);
        assertEquals(reviewDto, reviewDto1);
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void remove() {
    }
}