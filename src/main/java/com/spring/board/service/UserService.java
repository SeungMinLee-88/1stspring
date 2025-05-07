package com.spring.board.service;

import com.spring.board.dto.BoardFileDTO;
import com.spring.board.dto.UserDto;
import com.spring.board.entity.*;
import com.spring.board.repository.RoleRepository;
import com.spring.board.repository.RoleUserRepository;
import com.spring.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final RoleRepository roleRepository;
  private final RoleUserRepository roleUserRepository;



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


    userRepository.save(userEntity);
    for(int i = 0; i < userDto.getRoleUserSave().size(); i++) {
      System.out.println("roleList : " + userDto.getRoleUserSave().get(i));
      RoleEntity roleEntity = roleRepository.findById(userDto.getRoleUserSave().get(i)).get();
      RoleUserEntity roleUserEntity = RoleUserEntity.toSaveEntity(roleEntity, userEntity);
      RoleUserEntity roleUserEntitys = roleUserRepository.save(roleUserEntity);
    }

  }

  public List<UserDto> userList() {
    List<UserEntity> userEntityList = userRepository.findAll();
    ModelMapper mapper = new ModelMapper();
    List<UserDto> userDtoList = mapper.map(userEntityList, new TypeToken<List<UserDto>>() {
    }.getType());

    return userDtoList;

  }
}
