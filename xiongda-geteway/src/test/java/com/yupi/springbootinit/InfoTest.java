package com.yupi.springbootinit;

import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.inner.InnerUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InfoTest {


    @Autowired
     private InnerUserService innerUserService; //用户鉴权，查看数据库中是否存在对于的AK用户

    @Test
    void contextLoads() {
        User xiongda = innerUserService.getInvokerUser("xiongda");
        System.out.println(xiongda);

    }




}
