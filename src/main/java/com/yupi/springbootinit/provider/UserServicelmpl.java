package com.yupi.springbootinit.provider;

import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserServicelmpl implements UserService {
    @Override
    public String login(String name) {
        System.out.println("登录");
        return "登录成功"+name;
    }
}
