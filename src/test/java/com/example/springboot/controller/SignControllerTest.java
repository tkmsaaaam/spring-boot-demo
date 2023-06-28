package com.example.springboot.controller;

import com.example.springboot.model.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
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
    public void getTopIsOk() throws Exception {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new UserDetailsImpl("user", "password", authorities);
        mvc.perform(MockMvcRequestBuilders
                        .get("/")
                        .with(user(userDetails))
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().isOk())
                .andExpect(view().name("sign/index"));
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

    @Test
    @Sql("/sql/user-clear.sql")
    public void postSignupIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/signup")
                        .param("username", "name")
                        .param("email", "email@example.com")
                        .param("password", "password")
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @Sql("/sql/user-create.sql")
    public void postSignupIsNotOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/signup")
                        .param("username", "name")
                        .param("email", "email@example.com")
                        .param("password", "password")
                        .with(csrf())
                        .accept(MediaType.TEXT_HTML)
                ).andExpect(status().isOk())
                .andExpect(view().name("sign/signup"));
    }
}
