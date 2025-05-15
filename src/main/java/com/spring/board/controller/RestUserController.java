package com.spring.board.controller;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.RoleDTO;
import com.spring.board.dto.RoleUserDTO;
import com.spring.board.dto.UserDto;
import com.spring.board.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@ResponseBody
public class RestUserController {
  private final UserService userService;

  public RestUserController(UserService userService) {

    this.userService = userService;
  }

  @PostMapping("/userJoin")
  public ResponseEntity<String> userJoin(@RequestBody UserDto userDto) {

    System.out.println(userDto.getUserName());
    userService.joinUser(userDto);

    return new ResponseEntity<>("Join Success", HttpStatusCode.valueOf(200));
  }

  @PostMapping("/userUpdate")
  public ResponseEntity<String> userUpdate(@RequestBody UserDto userDto){
    System.out.println("userDetail userDto : " + userDto);

    userService.userUpdate(userDto);

    return new ResponseEntity<>("Update Success", HttpStatusCode.valueOf(200));
  }

  @GetMapping("/userList")
  public Page<UserDto> userList(@PageableDefault(page = 1) Pageable pageable, @RequestParam Map<String,String> params){

    Page<UserDto> userDtoList = userService.userList(pageable, params);

    return userDtoList;
  }

  @PostMapping("/userDetail")
  public UserDto userDetail(@RequestBody UserDto userDto){
    System.out.println("userDetail userDto : " + userDto);

    UserDto userDetail = userService.userDetail(userDto);

    return userDetail;
  }

  @PostMapping("/roleList")
  public List<RoleDTO> roleList(@RequestBody List<Long> roleIds){

    System.out.println("roleIds : " + roleIds);
    List<RoleDTO> roleDTOList = userService.roleList(roleIds);

    return roleDTOList;
  }


}
