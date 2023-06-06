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
public class CrudUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    public void getIndexIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/crud-user")
                        .with(user("user")
                                .password("password")
                                .roles("USER"))
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().isOk())
                .andExpect(view().name("crud-user/index"));
    }

    @Test
    public void getIndexIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/crud-user")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void getFormIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/crud-user/form")
                        .with(user("user")
                                .password("password")
                                .roles("USER"))
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().isOk())
                .andExpect(view().name("crud-user/form"));
    }

    @Test
    public void getFormIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/crud-user/form")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void postFormIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/crud-user/form")
                        .with(user("user")
                                .password("password")
                                .roles("USER"))
                        .with(csrf())
                        .param("name", "name")
                        .param("email", "email@example.com")
                        .param("password", "password")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/crud-user"));
    }

    @Test
    public void postFormINotLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/crud-user/form")
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void postFormIsWithoutCsrf() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/crud-user/form")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .param("name", "name")
                .param("email", "email@example.com")
                .param("password", "password")
                .accept(MediaType.TEXT_HTML)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void getEditIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/crud-user/edit/1")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }


    @Test
    @WithMockUser
    public void getEditIsUserNotPresented() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/crud-user/edit/1")
                        .with(user("user")
                                .password("password")
                                .roles("USER"))
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/crud-user/form"));
    }

    @Test
    @WithMockUser
    public void postEditIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/crud-user/edit/1")
                        .with(user("user")
                                .password("password")
                                .roles("USER"))
                        .with(csrf())
                        .param("name", "name")
                        .param("email", "email@example.com")
                        .param("password", "password")
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/crud-user"));
    }

    @Test
    @WithMockUser
    public void postEditIs4xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/crud-user/edit/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .param("name", "name")
                .param("email", "email@example.com")
                .param("password", "password")
                .accept(MediaType.TEXT_HTML)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void postEditIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/crud-user/edit/1")
                        .with(csrf())
                        .param("name", "name")
                        .param("email", "email@example.com")
                        .param("password", "password")
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser
    public void postDeleteIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/crud-user/delete/1")
                        .with(user("user")
                                .password("password")
                                .roles("USER"))
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/crud-user"));
    }

    @Test
    @WithMockUser
    public void postDeleteIs4xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/crud-user/delete/1")
                .with(user("user")
                        .password("password")
                        .roles("USER"))
                .accept(MediaType.TEXT_HTML)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    public void postDeleteIs3xx() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/crud-user/delete/1")
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
