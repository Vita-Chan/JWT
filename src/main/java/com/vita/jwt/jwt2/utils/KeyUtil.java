package com.vita.jwt.jwt2.utils;

import com.vita.jwt.jwt2.constant.Constant;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

public class KeyUtil {
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

}
