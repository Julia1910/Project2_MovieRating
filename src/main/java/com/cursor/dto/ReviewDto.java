package com.cursor.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private MovieDto movie;
    private String reviewMessage;
    private Boolean liked;
}
