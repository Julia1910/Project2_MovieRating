package com.cursor.service;

import com.cursor.dto.ReviewDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    void addReview() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setLiked(false);
        reviewDto.setReviewMessage("Bad movie");
        reviewDto.setMovieId(1L);
        ReviewDto reviewDto1 = reviewService.add(reviewDto);
        assertEquals(reviewDto, reviewDto1);
    }

    @Test
    void getReviewById() {
        ReviewDto reviewDto = reviewService.getById(6L);
        System.out.println(reviewDto);
    }

    @Test
    void getAll() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setLiked(true);
        reviewDto.setReviewMessage("Good movie");
        reviewDto.setMovieId(1L);
        List<ReviewDto> expectedReviews = new ArrayList<>();

        ReviewDto reviewDto1 = new ReviewDto();
        reviewDto1.setLiked(false);
        reviewDto1.setReviewMessage("Bad movie");
        reviewDto1.setMovieId(1L);

        expectedReviews.add(reviewDto);
        expectedReviews.add(reviewDto1);
        expectedReviews.add(reviewDto1);

        List<ReviewDto> getReviews = reviewService.getAll();
        assertEquals(expectedReviews, getReviews);
    }

    @Test
    void remove() {
        ReviewDto expectedReviewDto = new ReviewDto();
        expectedReviewDto.setLiked(true);
        expectedReviewDto.setReviewMessage("Good movie");
        expectedReviewDto.setMovieId(1L);

        ReviewDto actualReviewDto = reviewService.remove(5l);

        assertEquals(expectedReviewDto, actualReviewDto);
    }
}