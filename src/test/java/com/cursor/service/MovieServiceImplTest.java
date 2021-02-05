package com.cursor.service;

import com.cursor.dto.MovieDto;
import com.cursor.exceptions.NotFoundException;
import com.cursor.model.enums.Category;
import com.cursor.service.MovieServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;


// TODO: Not all tests will pass now due to missing logic.
//  Please implement the missing logic to pass TestAllGetByRatingDesc / Asc test
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieServiceImplTest {
    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Test
    @Order(1)
    void TestAdd() {
        MovieDto movieDto = new MovieDto();
        int i = 0;
        for (Category category : Category.values()) {
            i++;
            movieDto.setTitle(String.format("some movie %s", i));
            movieDto.setDescription(String.format("some movie description %s", i));
            movieDto.setCategory(Collections.singletonList(category));
            Assert.assertNotNull(movieServiceImpl.add(movieDto));
            System.out.println(movieDto);
        }
    }

    @Test
    @Order(2)
    void TestFindById() throws NotFoundException {
        Long id = 69L;
        MovieDto movieDto = movieServiceImpl.getById(id);
        Assert.assertNotNull(String.format("record with id = %s not found", id), movieDto);
        System.out.println(movieDto);
    }

    @Test
    @Order(3)
    void TestFindAll() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAll();
        Assert.assertNotNull("movies not found", movieDtoList);
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(4)
    void TestGetAllByRatingDesc() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByRatingDesc();
        if (movieDtoList.size() == 0) {
            Assert.fail("movies not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(5)
    void TestAllGetByRatingAsc() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByRatingAsc();
        if (movieDtoList.size() == 0) {
            Assert.fail("movies not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(6)
    void TestGetAllByCategoryAction() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.ACT);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = action not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(7)
    void TestGetAllByCategoryComedy() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.COM);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = comedy not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(8)
    void TestGetAllByCategoryDetective() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.DET);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = detective not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(9)
    void TestGetAllByCategoryDrama() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.DRAM);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = drama not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(10)
    void TestGetAllByCategoryMusical() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.MUS);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = musical not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(11)
    void TestGetAllByCategoryRomance() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.ROM);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = romance not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(12)
    void TestGetAllByCategoryRomanticComedy() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.COM);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = romantic comedy not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(13)
    void TestGetAllByCategoryScienceFiction() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.SCI_FI);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = science fiction not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(14)
    void TestGetAllByCategoryThriller() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.THR);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = thriller not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(15)
    void TestGetAllByCategoryHorror() {
        List<MovieDto> movieDtoList = movieServiceImpl.getAllByCategory(Category.HOR);
        if (movieDtoList.size() == 0) {
            Assert.fail("movies with category = horror not found");
        }
        movieDtoList.forEach(System.out::println);
    }

    @Test
    @Order(16)
    void TestDelete() {
        movieServiceImpl.remove(69L);
    }

    @Test
    @Order(17)
    void testAddRate() {
        MovieDto expectedMovie = new MovieDto();
        expectedMovie.setTitle("Harry Potter");
        expectedMovie.setDescription("Movie about a boy, who survived");
        expectedMovie.setCategory(List.of(Category.DRAM));
        expectedMovie.setDirectors(List.of());

        MovieDto actualMovie = movieServiceImpl.addRate(expectedMovie, 7);

        expectedMovie.setRating(7.5);


        Assert.assertEquals(expectedMovie, actualMovie);
    }
}
