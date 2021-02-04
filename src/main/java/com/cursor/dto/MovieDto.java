package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MovieDto {
    private String title;
    private Set<Category> category;
    private String shortDescription;
    private Double rateValue;
    private List<DirectorDto> director;

    @Override
    public String toString() {
        return "MovieDto{" +
                "title='" + title + '\'' +
                ", category=" + category +
                ", shortDescription='" + shortDescription + '\'' +
                ", rateValue=" + rateValue +
                '}';
    }
}
