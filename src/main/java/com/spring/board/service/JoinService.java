package com.spring.board.service;

import com.spring.board.dto.JoinDTO;
import com.spring.board.dto.UserDto;
import com.spring.board.entity.UserEntity;
import com.spring.board.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public void joinProcess(UserDto userDto) {

    String loginId = userDto.getLoginId();
    String userName = userDto.getUserName();
    String userPassword = userDto.getUserPassword();

    Boolean isExist = userRepository.existsByLoginId(userName);

    if (isExist) {

      return;
    }

    UserEntity userEntity = new UserEntity();

    userEntity.setLoginId(loginId);
    userEntity.setUserName(userName);
    userEntity.setUserPassword(bCryptPasswordEncoder.encode(userPassword));
    userEntity.setUserRole("ROLE_ADMIN");

    userRepository.save(userEntity);
  }
}
