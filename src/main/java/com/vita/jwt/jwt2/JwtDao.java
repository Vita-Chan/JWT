package com.vita.jwt.jwt2;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class JwtDao {

  Map<String, String> map = new HashMap<>();

  public void addToken(String id, String token) {
    map.put(id, token);
  }

  public String getTokenById(String id) {
    return (String) map.get(id);
  }
}
