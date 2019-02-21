package com.vita.jwt.jwt1.controller;

import com.alibaba.fastjson.JSONObject;
import com.vita.jwt.jwt1.annotation.PassToken;
import com.vita.jwt.jwt1.annotation.UserLoginToken;
import com.vita.jwt.jwt1.entity.User;
import com.vita.jwt.jwt1.service.TokenService;
import com.vita.jwt.jwt1.service.UserServiceImpl;
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
  TokenService tokenService;
  //登录

  @GetMapping("/login")
  public Object login(User user){
    System.out.println("===============================");
    JSONObject jsonObject=new JSONObject();
    User userForBase=userService.findById(user.getId());
    if(userForBase==null){
      System.out.println("登录失败");
      jsonObject.put("message","登录失败,用户不存在");
      return jsonObject;
    }else {
      if (!userForBase.getPassword().equals(user.getPassword())){
        jsonObject.put("message","登录失败,密码错误");
        return jsonObject;
      }else {
        //登录成功后 存储token信息
        String token = tokenService.getToken(userForBase);
        System.out.println("token:"+token);
        jsonObject.put("token", token);
        jsonObject.put("user", userForBase);
        return jsonObject;
      }
    }
  }
  @UserLoginToken
  @GetMapping("/getUserLoginToken")
  public String getMessage(){
    System.out.println("你已通过验证");
    return "你已通过验证";
  }

  @PassToken
  @GetMapping("/getPassToken")
  public String getPassToken(){
    System.out.println("不需要通过验证");
    return "不需要通过验证";
  }


}