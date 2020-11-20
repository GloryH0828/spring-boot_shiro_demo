package com.gloryh.realm;

import com.gloryh.entity.Account;
import com.gloryh.service.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 该类用于重写 Shiro 的 授权和认证方法
 *
 * @author 黄光辉
 * @since 2020/10/10
 */
public class AccountRealm extends AuthorizingRealm {
  @Autowired private AccountService accountService;
  /**
   * 授权
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    // 获取当前用户信息
    Subject subject = SecurityUtils.getSubject();
    Account account = (Account) subject.getPrincipal();
    // 设置角色
    // Set集合存储角色，Set可以保证没有重复
    Set<String> roles = new HashSet<>();
    // 存入当前用户对应的角色信息
    roles.add(account.getRole());
    // 将获取到的角色信息存入 SimpleAuthorizationInfo 中
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    // 设置权限
    // 存入当前用户对应的权限信息
    info.addStringPermission(account.getPerms());

    return info;
  }

  /**
   * 认证
   *
   * @param authenticationToken
   * @return
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    // 创建一个UsernamePasswordToken ,用于登录操作
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    // 从UsernamePasswordToken中获取用户的用户名，然后使用service方法在数据库中查找该用户
    Account account = accountService.findByUsername(token.getUsername());
    // 找到该用户后进行密码验证
    if (account != null) {
      // 密码对比
      return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
    }
    // 未找到该用户，返回null
    return null;
  }
}
