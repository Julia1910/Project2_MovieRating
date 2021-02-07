package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private String title;
    private List<Category> categories;
    private String shortDescription;
    private Double rateValue;
    private List<DirectorDto> directors;
}
