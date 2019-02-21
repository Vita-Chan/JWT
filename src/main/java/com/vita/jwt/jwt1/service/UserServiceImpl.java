package com.vita.jwt.jwt1.service;

import com.vita.jwt.jwt1.entity.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
  Map<String, User> map = new HashMap<>();
  public User findById(String id){
    init();
    return map.get(id);
  }

  public void init(){
    map.put("1",new User("1","user","password"));
  }
}
