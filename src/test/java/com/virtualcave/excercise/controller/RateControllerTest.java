package com.virtualcave.excercise.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createRate() throws Exception {

        String rateAsJson = "{\"id\":2,\"brand_id\":2,\"product_id\":1,\"start_date\":\"2022-01-01T00:00:00.000+00:00\",\"end_date\":\"2020-12-31T00:00:00.000+00:00\",\"price\":1234,\"currency_code\":\"EUR\"}";

        MockHttpServletResponse response = this.mockMvc.perform(
                        post("/rate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(rateAsJson))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

}

