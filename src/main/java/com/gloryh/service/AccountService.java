package com.gloryh.service;

import com.gloryh.entity.Account;

/**
 * Account 相关服务接口类
 *
 * @author 黄光辉
 * @since 2020/10/10
 **/
public interface AccountService {
    /**
     * 按用户名查找用户信息
     * @param username 用户名
     * @return 用户信息
     */
    public Account findByUsername(String username);
}
