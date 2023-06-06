package com.example.springboot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SignControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getTopIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void getLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/login")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().isOk())
                .andExpect(view().name("sign/login"));
    }

    @Test
    public void getSignup() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/signup")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().isOk())
                .andExpect(view().name("sign/signup"));
    }
}
