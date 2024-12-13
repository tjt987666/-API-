package com.yupi.springbootinit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 */
@SpringBootTest
public class UserIntetfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userService;

    @Test
    void userRegister() {
        boolean b = userService.invokeCount(1, 1);
        System.out.println(b);
    }

}
