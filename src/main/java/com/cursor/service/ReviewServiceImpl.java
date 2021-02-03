package com.cursor.service;

import com.cursor.dao.MovieRepository;
import com.cursor.dao.ReviewRepository;
import com.cursor.dto.ReviewDto;
import com.cursor.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public ReviewDto add(ReviewDto reviewDto) {
        Review review = setReview(reviewDto);
        reviewRepository.save(review);
        return reviewDto;
    }

    @Override
    public ReviewDto getById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        ReviewDto reviewDto = setReviewDto(review);
        return reviewDto;
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
    public ReviewDto remove(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        ReviewDto reviewDto = setReviewDto(review);
        reviewRepository.delete(review);
        return reviewDto;
    }

    private Review setReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setMovie(movieRepository.findById(reviewDto.getMovieId()).orElseThrow());
        review.setLiked(reviewDto.getLiked());
        review.setReviewMessage(reviewDto.getReviewMessage());
        return review;
    }

    private ReviewDto setReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setMovieId(review.getMovie().getId());
        reviewDto.setLiked(review.getLiked());
        reviewDto.setReviewMessage(review.getReviewMessage());
        return reviewDto;
    }
}
