package com.spring.board.service;

import com.spring.board.dto.ReserveDTO;
import com.spring.board.dto.UserDto;
import com.spring.board.entity.UserEntity;
import com.spring.board.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService {


  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    System.out.println("call loadUserByUsername");

    //DB에서 조회
    UserEntity userData = userRepository.findByUsername(username);

    ModelMapper mapper = new ModelMapper();

    UserDto userDto  = mapper.map(userData, new TypeToken<UserDto>(){}.getType());
    System.out.println("userDto : " + userDto);


    if (userData != null) {

      //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
      return new CustomUserDetails(userData);
    }

    return null;
  }
}