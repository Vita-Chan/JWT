package com.vita.jwt.jwt1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * lombok注解:
 * @AllArgsConstructor - 会生成一个包含全部变量的构造函数
 * @NoArgsConstructor - 会生成一个无参的构造函数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  String Id;
  String username;
  String password;
}
