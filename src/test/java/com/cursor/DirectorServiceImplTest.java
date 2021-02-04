package com.cursor;

import com.cursor.dto.DirectorDto;
import com.cursor.dto.MovieDto;
import com.cursor.model.Director;
import com.cursor.model.Movie;
import com.cursor.model.enums.Category;
import com.cursor.service.DirectorServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DirectorServiceImplTest {

    public static DirectorDto createNewDirectorDto() {
        DirectorDto directorMax = new DirectorDto();
        directorMax.setFirstName("Max");
        directorMax.setLastName("Li");
        List<DirectorDto> directorDtos = new ArrayList<>();
        directorDtos.add(directorMax);
        Category dramDto = Category.DRAM;
        Set<Category> categoriesDto = new HashSet<>();
        categoriesDto.add(dramDto);
        Double rate = 7.0;
        List<MovieDto> gDto = new ArrayList<>();
        MovieDto movie1Dto = new MovieDto();
        movie1Dto.setTitle("Titanic");
        movie1Dto.setCategory(categoriesDto);
        movie1Dto.setShortDescription("Cry cry cry");
        movie1Dto.setRateValue(rate);
        movie1Dto.setDirector(directorDtos);
        gDto.add(movie1Dto);
        directorMax.setMovie(gDto);
        return directorMax;
    }

    public static Director createNewDirector() {
        Director directorMax = new Director();
        directorMax.setFirstName("Max");
        directorMax.setLastName("Li");
        List<Director> director = new ArrayList<>();
        director.add(directorMax);
        Category dram = Category.DRAM;
        Set<Category> categories = new HashSet<>();
        categories.add(dram);
        Double rate = 7.0;
        List<Movie> movieList = new ArrayList<>();
        Movie movie = new Movie();
        movie.setTitle("Titanic");
        movie.setCategory(categories);
        movie.setDescription("Cry cry cry");
        movie.setRating(rate);
        movie.setDirector(director);
        movieList.add(movie);
        directorMax.setMovie(movieList);
        return directorMax;
    }

    @Test
    public void entityShouldBeDto() {
        DirectorDto directorDtoExample = createNewDirectorDto();
        Director director = DirectorServiceImpl.dtoToEntity(directorDtoExample);
        DirectorDto directorDtoActual = DirectorServiceImpl.entityToDto(director);

        Assert.assertEquals(directorDtoExample.getFirstName(), directorDtoActual.getFirstName());
        Assert.assertEquals(directorDtoExample.getLastName(), directorDtoActual.getLastName());
        Assert.assertEquals(directorDtoExample.getMovie(), directorDtoActual.getMovie());  //do not works
    }
}

