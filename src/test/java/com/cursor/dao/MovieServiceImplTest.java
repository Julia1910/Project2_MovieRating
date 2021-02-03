package com.cursor.dao;

import com.cursor.dto.MovieDto;
import com.cursor.service.impls.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieServiceImplTest {
    @Autowired
    private MovieServiceImpl movieServiceImpl;

    @Test
    void TestFindById() {
        MovieDto m = movieServiceImpl.getById(1L);
        System.out.println(m);
    }

    @Test
    void TestFindAll(){
        List<MovieDto> movieDtoList = movieServiceImpl.getAll();
        System.out.println(movieDtoList);
    }
}
