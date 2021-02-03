package com.cursor.service;

import com.cursor.dao.ReviewRepository;
import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.dto.ReviewDto;
import com.cursor.model.Director;
import com.cursor.model.Movie;
import com.cursor.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public ReviewDto add(ReviewDto reviewDto) {
        Review review = setReview(reviewDto);
        reviewRepository.save(review);
        return reviewDto;
    }

    @Override
    public ReviewDto getById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        ReviewDto reviewDto = new ReviewDto();
        MovieDto movieDto = setMovieDto(review.getMovie());
        reviewDto.setMovie(movieDto);
        reviewDto.setLiked(review.getLiked());
        reviewDto.setReviewMessage(review.getReviewMessage());
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
        reviewRepository.delete(review);
        ReviewDto reviewDto = setReviewDto(review);
        return reviewDto;
    }

    private Review setReview(ReviewDto reviewDto) {
        Review review = new Review();
        Movie movie = setMovie(reviewDto.getMovie());
        review.setMovie(movie);
        review.setReviewMessage(reviewDto.getReviewMessage());
        review.setLiked(reviewDto.getLiked());
        return review;
    }

    private ReviewDto setReviewDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewMessage(review.getReviewMessage());
        reviewDto.setLiked(review.getLiked());
        MovieDto movieDto = setMovieDto(review.getMovie());
        reviewDto.setMovie(movieDto);
        return reviewDto;
    }

    private Movie setMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setRating(movieDto.getRateValue());
        movie.setTitle(movieDto.getTitle());
        movie.setCategory(movieDto.getCategory());
        movie.setDescription(movieDto.getShortDescription());
        List<Director> directors = setDirectors(movieDto.getDirectors());
        movie.setDirectors(directors);
        return movie;
    }

    private MovieDto setMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setCategory(movie.getCategory());
        movieDto.setTitle(movie.getTitle());
        movieDto.setShortDescription(movie.getDescription());
        movieDto.setRateValue(movie.getRating());
        List<DirectorDto> directorDtos = setDirectorsDto(movie.getDirectors());
        movieDto.setDirectors(directorDtos);
        return movieDto;
    }

    private List<Director> setDirectors(List<DirectorDto> directorDtos) {
        List<Director> directors = new ArrayList<>();
        for (DirectorDto directorDto : directorDtos) {
            Director director = new Director();
            director.setFirstName(directorDto.getFirstName());
            director.setLastName(directorDto.getLastName());
            directors.add(director);
        }
        return directors;
    }

    private List<DirectorDto> setDirectorsDto(List<Director> directors) {
        List<DirectorDto> directorDtos = new ArrayList<>();
        for (Director director : directors) {
            DirectorDto directorDto = new DirectorDto();
            directorDto.setFirstName(director.getFirstName());
            directorDto.setLastName(director.getLastName());
            directorDtos.add(directorDto);
        }
        return directorDtos;
    }
}
