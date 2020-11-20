package com.gloryh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gloryh.entity.Account;
import com.gloryh.mapper.AccountMapper;
import com.gloryh.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Account 相关服务接口实现类
 *
 * @author 黄光辉
 * @since 2020/10/10
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired private AccountMapper accountMapper;

  /**
   * 按用户名查找用户信息
   *
   * @param username 用户名
   * @return 用户信息
   */
  @Override
  public Account findByUsername(String username) {
    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq("username", username);
    return accountMapper.selectOne(wrapper);
  }
}
