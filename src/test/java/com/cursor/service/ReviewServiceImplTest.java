package com.cursor.service;

import com.cursor.dto.ReviewDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    @Order(1)
    void addReview() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setLiked(false);
        reviewDto.setReviewMessage("Bad movie");
        reviewDto.setMovieId(1L);
        ReviewDto reviewDto1 = reviewService.add(reviewDto);
        assertEquals(reviewDto, reviewDto1);
    }

    @Test
    @Order(2)
    void getReviewById() {
        ReviewDto reviewDto = reviewService.getById(5L);
        System.out.println(reviewDto);
    }

    @Test
    @Order(3)
    void getAll() {
        List<ReviewDto> getReviews = reviewService.getAll();
        assertNotNull(getReviews);
        System.out.println(getReviews);
    }

    @Test
    @Order(4)
    void remove() {
        reviewService.remove(49L);
    }
}