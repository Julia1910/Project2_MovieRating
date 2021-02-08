package com.cursor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private List<MovieDto> movies;

    @Override
    public String toString() {
        return "DirectorDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
