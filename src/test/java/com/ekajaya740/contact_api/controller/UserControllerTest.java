package com.ekajaya740.contact_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


import com.ekajaya740.contact_api.model.RegisterUserRequest;
import com.ekajaya740.contact_api.entity.*;
import com.ekajaya740.contact_api.model.WebResponse;
import com.ekajaya740.contact_api.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  @SuppressWarnings("unused")
  void setup() {
    userRepository.deleteAll();
  }

  @Test
  void testRegisterSuccess() throws Exception {
    RegisterUserRequest request = new RegisterUserRequest();

    request.setUsername("test");
    request.setPassword("testpass");
    request.setName("Test");
    mockMvc.perform(
        post("/api/users").with(httpBasic("user", "user")).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isOk()).andDo(result -> {
          WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
              new TypeReference<WebResponse<String>>() {

              });

          assertEquals("OK", response.getData());

        });
  }
  
  @Test
  void testRegisterBadRequest() throws Exception {
    RegisterUserRequest request = new RegisterUserRequest();

    request.setUsername("");
    request.setPassword("");
    request.setName("");
    mockMvc.perform(
        post("/api/users").with(httpBasic("user", "user")).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isBadRequest()).andDo(result -> {
          WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
              new TypeReference<WebResponse<String>>() {

              });

          assertNotNull(response.getErrors());
        });
  }
  
  @Test
  void testRegisterDuplicate() throws Exception {
    com.ekajaya740.contact_api.entity.User user = new com.ekajaya740.contact_api.entity.User();

    user.setUsername("test");
    user.setPassword(BCrypt.hashpw("testpass",BCrypt.gensalt()));
    user.setName("Test");

    userRepository.save(user);    


    RegisterUserRequest request = new RegisterUserRequest();

    request.setUsername("test");
    request.setPassword("testpass");
    request.setName("Test");
    mockMvc.perform(
        post("/api/users").with(httpBasic("user", "user")).accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(request)))
        .andExpectAll(status().isBadRequest()).andDo(result -> {
          WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
              new TypeReference<WebResponse<String>>() {

              });

              assertNotNull(response.getErrors());

        });
  }
}
