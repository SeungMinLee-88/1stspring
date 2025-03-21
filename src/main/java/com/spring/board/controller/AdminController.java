package com.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AdminController {
  @GetMapping("/admin")
  public String adminP() {
    System.out.println("call admin Controller");

    return "admin Controller";
  }
}
