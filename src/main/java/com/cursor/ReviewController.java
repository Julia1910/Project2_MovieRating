package com.cursor;

import com.cursor.dto.ReviewDto;
import com.cursor.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> add(@RequestBody ReviewDto reviewDto) {
        ReviewDto review = reviewService.add(reviewDto);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReviewDto>> getAll() {
        List<ReviewDto> reviews = reviewService.getAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> getById(@PathVariable("id") Long id) {
        ReviewDto reviewDto;
        reviewDto = reviewService.getById(id);
        if (reviewDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> remove(@PathVariable("id") Long id) {
        ReviewDto reviewDto = reviewService.getById(id);
        if (reviewDto == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        reviewService.remove(id);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }
}
