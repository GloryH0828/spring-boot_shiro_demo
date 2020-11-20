package com.gloryh.entity;

import lombok.Data;

/**
 * 用户登录实体类
 *
 * @author 黄光辉
 * @since 2020/8/26
 **/
@Data
public class Account {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 权限
     */
    private String perms;
    /**
     * 角色
     */
    private String role;
}
