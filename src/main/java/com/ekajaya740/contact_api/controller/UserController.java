package com.ekajaya740.contact_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ekajaya740.contact_api.model.RegisterUserRequest;
import com.ekajaya740.contact_api.model.WebResponse;
import com.ekajaya740.contact_api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
    userService.register(request);

    return WebResponse.<String>builder().data("OK").build();
  }

}
