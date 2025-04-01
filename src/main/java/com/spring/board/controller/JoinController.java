package com.spring.board.controller;

import com.spring.board.dto.JoinDTO;
import com.spring.board.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@ResponseBody
public class JoinController {
  private final JoinService joinService;

  public JoinController(JoinService joinService) {

    this.joinService = joinService;
  }

  @PostMapping("/join")
  public String joinProcess(@RequestBody JoinDTO joinDTO) {

    System.out.println(joinDTO.getUsername());
    joinService.joinProcess(joinDTO);

    return "ok";
  }
}
