package com.vita.jwt.jwt2.controller;

import com.google.gson.JsonObject;
import com.vita.jwt.jwt2.annotation.PassToken;
import com.vita.jwt.jwt2.annotation.UserLoginToken;
import com.vita.jwt.jwt2.JwtDao;
import com.vita.jwt.jwt2.constant.Constant;
import com.vita.jwt.jwt2.entity.User;
import com.vita.jwt.jwt2.service.UserServiceImpl;
import com.vita.jwt.jwt2.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class UserApi {

  @Autowired
  UserServiceImpl userService;

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  JwtDao jwtDao;

  @GetMapping("/login")
  public JsonObject login(User user) throws Exception {
    JsonObject jsonObject = new JsonObject();
    User userForBase = userService.findById(user.getId());
    if (userForBase == null) {
      jsonObject.addProperty("message", "登陆失败");
      return jsonObject;
    } else {
      if (!userForBase.getPassword().equals(user.getPassword())) {
        jsonObject.addProperty("message", "登录失败,密码错误");
        return jsonObject;
      } else {
        //登录成功后 存储token信息
        String token = jwtUtil.createJWT(user);
        jwtDao.addToken(user.getId(), token);
        System.out.println("token:" + token);
        jsonObject.addProperty("token", token);
        return jsonObject;
      }
    }
  }


  @GetMapping("/refresh/thoken")
  public JsonObject refreshToke(String id) throws Exception {
    JsonObject jsonObject = new JsonObject();
    User user = userService.findById(id);
    String token = jwtUtil.createJWT(user);
    jwtDao.addToken(user.getId(),token);
    long ttlMillis = Constant.JWT_TTL;
    long nowMillis = System.currentTimeMillis();
    long expMillis = nowMillis + ttlMillis;
    jsonObject.addProperty("token",token);
    jsonObject.addProperty("expMillis",expMillis - (5000*60));
    return jsonObject;
  }

  @UserLoginToken
  @GetMapping("/getUserLoginToken")
  public String getMessage() {
    System.out.println("你已通过验证");
    return "你已通过验证";
  }

  @PassToken
  @GetMapping("/getPassToken")
  public String getPassToken() {
    System.out.println("不需要通过验证");
    return "不需要通过验证";
  }


}