package com.yupi.springbootinit.utils;

import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.Inner.InnerInterfaceInfoServiceImp;
import com.yupi.springbootinit.service.Inner.InnerUserInterfaceInfoServiceImp;
import com.yupi.springbootinit.service.UserInterfaceInfoServiceImpl;
import com.yupi.springbootinit.service.inner.InnerUserService;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    private InnerUserService innerUserService;


    @Autowired
    private InnerUserInterfaceInfoServiceImp userInterfaceInfoService;

    @Autowired
    private InnerInterfaceInfoServiceImp interfaceInfoService;



    @Test
    public void test() {
        User user = innerUserService.getInvokerUser("xiongda");
        System.out.println("AK:"+user.getAccessKey());

        boolean b = userInterfaceInfoService.InvokeCount(1, 1);
        System.out.println("操作成功"+b);


        InterfaceInfo post = interfaceInfoService.getInterfaceInfo("api/name/user", "POST");
        System.out.println(post);


    }

}
