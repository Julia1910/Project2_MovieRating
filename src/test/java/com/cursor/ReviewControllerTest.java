package com.cursor;

import com.cursor.dao.ReviewRepository;
import com.cursor.dto.ReviewDto;
import com.cursor.service.ReviewServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
public class ReviewControllerTest {

    @Autowired
    protected WebApplicationContext context;
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    protected MockMvc mockMvc;

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldGetAllReviews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = {"/AddMovieForReview.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    void addReview() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewMessage("best of the best of the best");
        reviewDto.setLiked(true);
        reviewDto.setMovieId(2L);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reviews/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(OBJECT_MAPPER.writeValueAsString(reviewDto));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @Sql(scripts = {"/Data.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void shouldGetReviewById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reviews/5"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldDeleteReviewById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/reviews/23"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
