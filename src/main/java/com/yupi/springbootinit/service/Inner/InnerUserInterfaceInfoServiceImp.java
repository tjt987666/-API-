package com.yupi.springbootinit.service.Inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.UserInterfaceInfoMapper;
import com.yupi.springbootinit.model.entity.UserInterfaceInfo;
import com.yupi.springbootinit.service.UserInterfaceInfoServiceImpl;
import com.yupi.springbootinit.service.inner.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImp implements InnerUserInterfaceInfoService {


    @Resource
    private UserInterfaceInfoServiceImpl userInterfaceInfoService;
    /**
     * 统计接口调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean InvokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    /**
     * 查看用户剩余接口调用次数
     * @param userId
     * @return
     */
    @Override
    public boolean checkInterfaceInfo(long userId) {
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        System.out.println("用户接口次数");
        UserInterfaceInfo one = userInterfaceInfoService.getOne(queryWrapper);
        System.out.println(one);
        if (one.getInvokenum() <= 0 ){
            return false;
        }
        return true;
    }
}
