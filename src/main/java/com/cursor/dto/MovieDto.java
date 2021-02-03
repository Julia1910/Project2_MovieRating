package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.Data;

import java.util.List;

@Data
public class MovieDto {
    private String title;
    private List<Category> category;
    private String shortDescription;
    private Double rateValue;
    private List<DirectorDto> director;
}
