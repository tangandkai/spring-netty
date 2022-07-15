package com.tang.boot.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("===before class===");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("===before method===");
    }

    @Test
    void hello() throws Exception {
        MvcResult result = mvc.perform(get("/")
                .param("name", "IMOOC")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println("==="+result.getResponse().getContentAsString()+"===");
    }

    @Test
    void hello_2() {
    }

}