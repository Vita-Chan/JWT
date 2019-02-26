package com.vita.jwt.jwt2.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.vita.jwt.jwt2.entity.User;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  /**
   * 获得token
   */
  public String getToken(User user) {
    String token = "";

    //Algorithm.HMAC256() - 使用HS256生成token,密钥则是用户的密码，唯一密钥的话可以保存在服务端。
    //withAudience - 存入需要保存在token的信息，这里我把用户ID存入token中
    token = JWT.create().withAudience(user.getId())
        .sign(Algorithm.HMAC256(user.getPassword()));
    return token;
  }
}
