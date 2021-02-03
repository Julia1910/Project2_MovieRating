package com.cursor.dto;

import com.cursor.model.enums.Category;
import lombok.Data;

import java.util.List;

@Data
public class MovieRequestDto extends MovieDto{
    private String title;
    private List<Category> category;
    private String shortDescription;
}
