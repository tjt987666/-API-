package com.yupi.springbootinit.service.Inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.UserServiceImpl;
import com.yupi.springbootinit.service.inner.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;
import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImp implements InnerUserService {

    @Resource
    private UserServiceImpl userService;



    /**
     * 查询数据库中用户是否含有该accessKey
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokerUser(String accessKey) {
        System.out.println("用户发起接口请求......");
        System.out.println("accessKey"+accessKey);

        User accessKey1 = userService.getOne(new QueryWrapper<User>().eq("accessKey", accessKey));


        return accessKey1;
    }

}
