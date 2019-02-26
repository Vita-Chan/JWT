package com.vita.jwt.jwt2.factory;

import com.vita.jwt.jwt2.entity.JwtUser;
import com.vita.jwt.jwt2.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserFactory {
  private JwtUserFactory() {
  }

  public static JwtUser create(User user) {
    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        mapToGrantedAuthorities(user.getRoles())
    );
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
    return authorities.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
