package com.example.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SignControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void getLogin() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/login").accept(MediaType.TEXT_HTML))
      .andExpect(status().isOk());
  }
}
