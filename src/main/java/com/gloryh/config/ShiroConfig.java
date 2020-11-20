package com.gloryh.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.gloryh.realm.AccountRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.Map;

/**
 * Shiro 相关配置类
 *
 * @author 黄光辉
 * @since 2020/10/10
 */
@Configuration
public class ShiroConfig {
  /**
   * 配置 Realm
   *
   * @return AccountRealm
   */
  @Bean
  public AccountRealm accountRealm() {
    return new AccountRealm();
  }
  /** 配置 DefaultWebSecurityManager,将配置好的 AccountRealm 注入 */
  @Bean
  public DefaultWebSecurityManager defaultWebSecurityManager(
      @Qualifier("accountRealm") AccountRealm accountRealm) {
    DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
    manager.setRealm(accountRealm);
    return manager;
  }

  /**
   * 配置 ShiroFilterFactoryBean，将配置好的 DefaultWebSecurityManager 注入。
   * 配置 权限设置
   *
   * @return
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(
      @Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(defaultWebSecurityManager);
    //权限设置
    Map<String, String> map =new Hashtable<>();
    map.put("/main","authc");
    map.put("/manage", "perms[manage]");
    map.put("/administrator","roles[administrator]");
    factoryBean.setFilterChainDefinitionMap(map);
    //配置认证失败后要跳转的登录页面
    factoryBean.setLoginUrl("/login");
    //设置显示未授权页面
    factoryBean.setUnauthorizedUrl("/unauthorized");
    return factoryBean;
  }

  @Bean
  public ShiroDialect shiroDialect(){
    return new ShiroDialect();
  }
}
