package com.cursor.dto;

import lombok.Data;

import java.util.List;

@Data
public class DirectorDto {
    private String firstName;
    private String lastName;
    private List<MovieDto> movies;

    @Override
    public String toString() {
        return "DirectorDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
