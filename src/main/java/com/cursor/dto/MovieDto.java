package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.Data;

import java.util.List;

@Data
public class MovieDto {
    private String title;
    private List<Category> category;
    private String description;
    private Double rating;
    private List<DirectorDto> director;
}
