package com.vita.jwt.jwt2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * springSecurity 规定必须的user类
 */
public class JwtUser implements UserDetails {

  private final String id;
  private final String username;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  public JwtUser(String id, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  /**
   * 返回分配给用户的角色列表
   * @return
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  /**
   * 账户是否未过期
   * @return
   */
  @JsonIgnore  //将这个bean转化为json的时候 忽略这个属性
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * 账户是否未锁定
   * @return
   */
  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * 密码是否未过期
   * @return
   */
  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * 账户是否激活
   * @return
   */
  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }
}
