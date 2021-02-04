package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private String title;
    private Set<Category> category;
    private String shortDescription;
    private Double rateValue;
    private List<DirectorDto> directors;
}
