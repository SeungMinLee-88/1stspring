package com.spring.board.service;

import com.spring.board.entity.RoleEntity;
import com.spring.board.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails  implements UserDetails {

  private final UserEntity userEntity;
  private final List<RoleEntity> roleEntity;

  public CustomUserDetails(UserEntity userEntity, List<RoleEntity> roleEntity) {

    this.userEntity = userEntity;
      this.roleEntity = roleEntity;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Collection<GrantedAuthority> collection = new ArrayList<>();

    collection.add(new GrantedAuthority() {

      @Override
      public String getAuthority() {
        //return "";
        return roleEntity.toString();
      }
    });

    return collection;
  }

  @Override
  public String getPassword() {

    return userEntity.getUserPassword();
  }

  @Override
  public String getUsername() {

    return userEntity.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  @Override
  public boolean isEnabled() {

    return true;
  }
}