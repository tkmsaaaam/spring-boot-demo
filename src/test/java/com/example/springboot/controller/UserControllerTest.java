package com.example.springboot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void createIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/add")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .with(csrf())
                .param("name", "name")
                .param("email", "email@example.com")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void createIs4xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/add")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .param("name", "name")
                .param("email", "email@example.com")
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void createIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/add")
                        .with(csrf())
                        .param("name", "name")
                        .param("email", "email@example.com")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void getIdIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/user/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    public void getIdIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/1")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void getIndexIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/user/all")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
        ).andExpect(status().isOk());
    }

    @Test
    public void getIndexIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/user/all")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser
    public void updateIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/edit/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .with(csrf())
                .param("name", "name")
                .param("email", "email@example.com")
        ).andExpect(status().isOk());
    }

    @Test
    public void updateIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/edit/1")
                        .with(csrf())
                        .param("name", "name")
                        .param("email", "email@example.com")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void updateIs4xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/edit/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .param("name", "name")
                .param("email", "email@example.com")
        ).andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser
    public void deleteUserIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/delete/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteUserIs4xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/user/delete/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteUserIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/user/delete/1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
