package com.spring.board.controller;

import com.spring.board.dto.UserDto;
import com.spring.board.service.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@ResponseBody
public class RestUserController {
  private final UserService userService;

  public RestUserController(UserService userService) {

    this.userService = userService;
  }

  @PostMapping("/join")
  public ResponseEntity<String> joinProcess(@RequestBody UserDto userDto) {

    System.out.println(userDto.getUserName());
    userService.joinProcess(userDto);

    return new ResponseEntity<>("Join Success", HttpStatusCode.valueOf(200));
  }

  @GetMapping("/userList")
  public List<UserDto> userList() {

    List<UserDto> userDtoList = userService.userList();

    return userDtoList;
  }
}
