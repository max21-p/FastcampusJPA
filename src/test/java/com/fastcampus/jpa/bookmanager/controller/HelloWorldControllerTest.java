package com.fastcampus.jpa.bookmanager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
// 1. @MockBean(JpaMetamodelMappingContext.class)
// 3. @SpringBootTest
class HelloWorldControllerTest {

// 3.   @Autowired
// 3.   private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

// 3.   @BeforeEach
// 3.   void before() {
// 3.       mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
// 3.   }

    @Test
    void helloWorld() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/hello-world"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello-world"));
    }

}