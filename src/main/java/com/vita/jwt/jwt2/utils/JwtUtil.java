package com.vita.jwt.jwt2.utils;

import com.google.gson.Gson;
import com.vita.jwt.jwt2.constant.Constant;
import com.vita.jwt.jwt2.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
  /**
   * 由字符串生成加密key
   *
   * @return
   */
  public SecretKey generalKey() {
    String stringKey = Constant.JWT_SECRET;

    // 本地的密码解码
    byte[] encodedKey = Base64.decodeBase64(stringKey);

    // 根据给定的字节数组使用AES加密算法构造一个密钥
    SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

    return key;
  }

  /**
   * 创建jwt
   * @param ttlMillis 过期时间
   * @return
   * @throws Exception
   */
  public String createJWT(long ttlMillis) throws Exception {
    User user = new User("1","user","pwd");
    // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 生成JWT的时间
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
    Map<String, Object> claims = new HashMap<>();
    claims.put("user", user);

    // 下面就是在为payload添加各种标准声明和私有声明了
    // 这里其实就是new一个JwtBuilder，设置jwt的body
    JwtBuilder builder = Jwts.builder()
        // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
        .setClaims(claims)
        // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
        .setId(user.getId())
        // iat: jwt的签发时间
        .setIssuedAt(now)
        // issuer：jwt签发人
        .setIssuer("Vita")
        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
        .setSubject(new Gson().toJson(user))
        // 生成签名时 设置签名使用的签名算法和签名使用的秘钥(自己设置的 不能外露)
        .signWith(signatureAlgorithm, generalKey());

    // 设置过期时间
    if (ttlMillis >= 0) {
      long expMillis = nowMillis + ttlMillis;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }
    return builder.compact();
  }

  /**
   * 解密jwt
   * @param jwt
   * @return
   * @throws Exception
   */
  public Claims parseJWT(String jwt) throws Exception {
    SecretKey key = generalKey();           //签名秘钥，和生成的签名的秘钥一模一样
    Claims claims = Jwts.parser()           //得到DefaultJwtParser
        .setSigningKey(key)                 //设置签名的秘钥
        .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
    return claims;
  }

  public static void main(String[] args) {

    User user = new User("tingfeng", "bulingbuling", "1056856191");
    String subject = new Gson().toJson(user);

    try {
      JwtUtil util = new JwtUtil();
      String jwt = util.createJWT(Constant.JWT_TTL);
      System.out.println("JWT：" + jwt);

      System.out.println("\n解密\n");

      Claims c = util.parseJWT(jwt);
      System.out.println(c.getId());
      System.out.println(c.getIssuedAt());
      User user1 = new Gson().fromJson(c.getSubject(),User.class);
      System.out.println(c.getIssuer());
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
