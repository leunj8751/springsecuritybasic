package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

  @GetMapping("/welcome")
  public String sayWelcome(){
    return "welcome to Spring Security Basic with security";
  }


}
