package com.cursor.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long movieId;
    private String reviewMessage;
    private Boolean liked;
}
