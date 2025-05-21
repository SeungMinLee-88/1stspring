package com.spring.board.service;

import com.spring.board.dto.*;
import com.spring.board.entity.*;
import com.spring.board.repository.RoleRepository;
import com.spring.board.repository.RoleUserRepository;
import com.spring.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {

  void userUpdate(UserDto userDto);

  void joinUser(UserDto userDto);

  Page<UserDto> userList(Pageable pageable, Map<String, String> params);

  UserDto userDetail(UserDto userDto);

  List<RoleDTO> roleList(List<Long> roleIds);
}
