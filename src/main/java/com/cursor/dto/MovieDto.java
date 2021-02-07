package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MovieDto {
    private String title;
    private Set<Category> category;
    private String description;
    private Double rating;
    private List<DirectorDto> directors;
}
