package com.vita.jwt.jwt2.Interceptor;

import com.google.gson.Gson;
import com.vita.jwt.jwt2.annotation.PassToken;
import com.vita.jwt.jwt2.annotation.UserLoginToken;
import com.vita.jwt.jwt2.service.UserServiceImpl;
import com.vita.jwt.jwt2.JwtDao;
import com.vita.jwt.jwt2.entity.User;
import com.vita.jwt.jwt2.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 创建一个拦截器去获取token并验证token
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private JwtDao jwtDao;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, Object object)
      throws Exception {
    String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
    // 如果不是映射到方法直接通过 - 如果不是HandlerMethod的实例直接通过
    // HandlerMethod - 负责对请求路径进行匹配并调用相应地执行方法。
    if (!(object instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod handlerMethod = (HandlerMethod) object;
    //获得控制器要请求的 请求方法
    Method method = handlerMethod.getMethod();
    //判断方法中是否有指定注解类型的注解
    if (method.isAnnotationPresent(PassToken.class)) {
      //获取这个注解 并判断是不是必须的, 是的话返回true, 跳过验证
      if (method.getAnnotation(PassToken.class).required()) {
        return true;
      }
    }

    //检查用户 需不需要权限的注解
    if (method.isAnnotationPresent(UserLoginToken.class)) {
      UserLoginToken userLoginToken = method.getDeclaredAnnotation(UserLoginToken.class);
      //如果是必须的 就进行验证
      if (userLoginToken.required()) {
        if (token == null) {
          throw new RuntimeException("没有token, 请重新登录");
        }
        //JWT根据token获取userId
        Claims claims = new JwtUtil().parseJWT(token);
        User u = new Gson().fromJson(claims.getSubject(), User.class);
        if (u == null) {
          throw new RuntimeException("用户不存在,请重新登录");
        }
        if (!jwtDao.getTokenById(u.getId()).equals(token)) {
          throw new RuntimeException("token不正确");
        }
        return true;
      }
  }
    return false;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o, Exception e) throws Exception {
  }
}
