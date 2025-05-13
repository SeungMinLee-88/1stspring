package com.spring.board.service;

import com.spring.board.dto.ReserveDTO;
import com.spring.board.dto.RoleUserDTO;
import com.spring.board.dto.UserDto;
import com.spring.board.entity.UserEntity;
import com.spring.board.repository.RoleUserRepository;
import com.spring.board.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService  implements UserDetailsService {


  @Autowired
  private UserRepository userRepository;
  private RoleUserRepository roleUserRepository;


  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

    System.out.println("call loadUserByUsername");

    System.out.println("loadUserByUsername loginId : " + loginId);
    //DB에서 조회
    UserEntity userData = userRepository.findByLoginId(loginId);

    ModelMapper mapper = new ModelMapper();
    System.out.println("loadUserByUsername userData : " + userData);
    /*UserDto userDto  = mapper.map(userData, new TypeToken<UserDto>(){}.getType());
    System.out.println("loadUserByUsername userDto : " + userDto);*/
/*    UserDto userDto = new UserDto(userData.getId(), userData.getLoginId(), userData.getUserName(), userData.getUserPassword(), mapper.map(roleUserRepository.findByUserEntity(userData)
            , new TypeToken<List<RoleUserDTO>>() {
            }.getType())
            );
    System.out.println("loadUserByUsername userDto : " + userDto);*/


    if (userData != null) {

      //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
      return new CustomUserDetails(userData);
    }

    return null;
  }
}