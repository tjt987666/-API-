package com.yupi.springbootinit.dubbo;

import com.yupi.springbootinit.provider.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class UserDubbo {
//
    @DubboReference
    private UserService userService;

    @PostConstruct
    public void logins()
    {
        String login = userService.login("你好");
        System.out.println(login);

    }


}
