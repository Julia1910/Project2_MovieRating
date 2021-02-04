package com.cursor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectorDto {
    private String firstName;
    private String lastName;
    private List<MovieDto> movie;
}
