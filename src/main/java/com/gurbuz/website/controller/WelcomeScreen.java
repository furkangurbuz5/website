package com.gurbuz.website.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeScreen {


  @GetMapping("/")
  public String welcome(){
    return "welcome";
  }
}
