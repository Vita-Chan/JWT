package com.vita.jwt.jwt2.constant;

import java.util.UUID;

public class Constant {
  //获取 UU ID
  public static final String JWT_ID = UUID.randomUUID().toString();

  /**
   * 加密密文
   */
  public static final String JWT_SECRET = "woyebuzhidaoxiediansha";

  //时间
  public static final int JWT_TTL = 60*60*1000;  //millisecond
}