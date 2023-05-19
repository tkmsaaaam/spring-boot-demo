package com.example.springboot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/user/all").accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void getId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/user/id").accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void postUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/user/add"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void editUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/user/edit/id"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/user/delete/id"))
                .andExpect(status().is4xxClientError());
    }
}
