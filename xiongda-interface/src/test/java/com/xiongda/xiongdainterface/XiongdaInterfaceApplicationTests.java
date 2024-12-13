package com.xiongda.xiongdainterface;

import com.xiongda.xiongclientsdk.cilent.XiongdaClient;
import com.xiongda.xiongclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class XiongdaInterfaceApplicationTests {

    @Resource
    private XiongdaClient  xiongdaClient;


    @Test
    void contextLoads() {
        String result = xiongdaClient.getName("xiongda");
        User user = new User();
        user.setUsername("xiongda");
        String userNmae = xiongdaClient.getUserNmae(user);
        System.out.println(result);
        System.out.println(userNmae);




    }

}
