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

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final RoleRepository roleRepository;
  private final RoleUserRepository roleUserRepository;



  public void userUpdate(UserDto userDto) {

    Optional<UserEntity> optionalUserEntity = userRepository.findById(userDto.getId());

    if (optionalUserEntity.isPresent()) {


      UserEntity userEntity = UserEntity.toSaveEntity(userDto);
      userEntity.setUserPassword(bCryptPasswordEncoder.encode(userDto.getUserPassword()));

      userRepository.save(userEntity);
      for (int i = 0; i < userDto.getRoleUserSave().size(); i++) {
        System.out.println("roleList : " + userDto.getRoleUserSave().get(i));
        RoleEntity roleEntity = roleRepository.findById(userDto.getRoleUserSave().get(i)).get();
        RoleUserEntity roleUserEntity = RoleUserEntity.toSaveEntity(roleEntity, userEntity);
        roleUserRepository.save(roleUserEntity);
      }
    }else{
    return;
    }

  }


  public void joinUser(UserDto userDto) {

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

  public Page<UserDto> userList(Pageable pageable, Map<String, String> params) {

    int page = pageable.getPageNumber() - 1;
    int pageLimit = 3;
    Specification<UserEntity> specification = new UserSpecification(new SearchCriteria(params.get("searchKey"), params.get("searchValue")));
    /*Page<UserEntity> userEntityList = userRepository.findAllWithPageble(specification, PageRequest.of(page, 3));*/
    Page<UserEntity> userEntityList = userRepository.findAll(specification, PageRequest.of(page, 3));

    /*Page<UserEntity> userEntityList = userRepository.findAllWithPageble(PageRequest.of(page, 3));
    ModelMapper mapper = new ModelMapper();
    Page<UserDto> userDtoList = mapper.map(userEntityList, new TypeToken<Page<UserDto>>() {
    }.getType());*/

    ModelMapper mapper = new ModelMapper();
    Page<UserDto> userDtoList = userEntityList.map(user -> new UserDto(user.getId(), user.getLoginId(), user.getUserName(), user.getUserPassword(), mapper.map(roleUserRepository.findByUserEntity(user)
            , new TypeToken<List<RoleUserDTO>>() {
            }.getType())
    )
    );
    for(int i=0;i<userEntityList.getSize();i++){

    }
    return userDtoList;
  }

  public UserDto userDetail(UserDto userDto) {

    /*Page<UserEntity> userEntityList = userRepository.findAllWithPageble(specification, PageRequest.of(page, 3));*/
    Optional<UserEntity> userEntityOptional = userRepository.findById(userDto.getId());

    if(userEntityOptional.isPresent()){
      UserEntity userEntity = userEntityOptional.get();
      ModelMapper mapper = new ModelMapper();
      UserDto userDetail = new UserDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUserName(), userEntity.getUserPassword(), mapper.map(roleUserRepository.findByUserEntity(userEntity)
              , new TypeToken<List<RoleUserDTO>>() {
              }.getType())
      );


      return userDetail;

    }else {
      return null;
    }

    public List<RoleDTO> roleList(Map<String, String> params) {


      List<RoleEntity> roleEntity = roleRepository.findAllNotIn();
      ModelMapper mapper = new ModelMapper();
      List<RoleDTO> roleDTOList =  mapper.map(roleUserRepository.findByUserEntity(roleEntity)
              , new TypeToken<List<RoleUserDTO>>() {
              }.getType());

      return roleDTOList;
    }

    /*Page<UserEntity> userEntityList = userRepository.findAllWithPageble(PageRequest.of(page, 3));
    ModelMapper mapper = new ModelMapper();
    Page<UserDto> userDtoList = mapper.map(userEntityList, new TypeToken<Page<UserDto>>() {
    }.getType());*/


  }
}
