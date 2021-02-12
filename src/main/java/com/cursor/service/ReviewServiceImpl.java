package com.cursor.service;

import com.cursor.dao.MovieRepository;
import com.cursor.dao.ReviewRepository;
import com.cursor.dto.ReviewDto;
import com.cursor.model.Review;
import com.cursor.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }


    @Override
    public ReviewDto add(ReviewDto reviewDto) {
        Review review = setReview(reviewDto);
        reviewRepository.save(review);
        return reviewDto;
    }

    @Override
    public ReviewDto getById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        return setReviewDto(review);
    }

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            ReviewDto reviewDto = setReviewDto(review);
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }

    @Override
    public void remove(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        reviewRepository.delete(review);
    }

       public Review setReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setMovie(movieRepository.findById(reviewDto.getMovieId()).orElseThrow());
        review.setLiked(reviewDto.getLiked());
        review.setReviewMessage(reviewDto.getReviewMessage());
        return review;
    }

        public ReviewDto setReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(review.getMovie().getId());
        reviewDto.setLiked(review.getLiked());
        reviewDto.setReviewMessage(review.getReviewMessage());
        return reviewDto;
    }
}
