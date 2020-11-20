package com.gloryh.controller;

import com.gloryh.entity.Account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Account 对应的前后端交互方法类
 *
 * @author 黄光辉
 * @since 2020/10/10
 */
@Controller
public class AccountController {
  @GetMapping("/{url}")
  public String redirect(@PathVariable("url") String url) {
    return url;
  }

  @PostMapping("/login")
  public String login(String username, String password, Model model) {
    // 创建一个 Subject 对象
    Subject subject = SecurityUtils.getSubject();
    // 创建一个 UsernamePasswordToken ,将 用户名 和密码 存入token
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    // 使用 对应的 login 方法 调用 Realm 的认证方法进行用户认证判断用户是否存在
    try {
      subject.login(token);
      // 获取登录信息
      Account account = (Account) subject.getPrincipal();
      // 存入session
      subject.getSession().setAttribute("account", account);
      // 成功，返回 index
      return "index";
    } catch (UnknownAccountException e) {
      // 用户名出错，抛出异常返回login重新登陆,并返回错误信息
      model.addAttribute("msg", "用户名输入有误");
      e.printStackTrace();
      return "login";
    } catch (IncorrectCredentialsException e) {
      // 密码出错，抛出异常返回login重新登陆,并返回错误信息
      model.addAttribute("msg", "密码输入有误");
      e.printStackTrace();
      return "login";
    }
  }

  @GetMapping("/unauthorized")
  public String unauthorized() {
    return "unauthorized";
  }

  @GetMapping("/logout")
  public String logout() {
    SecurityUtils.getSubject().logout();
    return "login";
  }
}
