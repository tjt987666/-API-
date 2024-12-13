package com.yupi.springbootinit.service.Inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.springbootinit.mapper.InterfaceInfoMapper;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.service.InterfaceInfoServiceImpl;
import com.yupi.springbootinit.service.inner.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 接口管理
 */
@DubboService
public class InnerInterfaceInfoServiceImp implements InnerInterfaceInfoService {


    @Resource
    private InterfaceInfoServiceImpl interfaceInfoService;

    /**
     * 判断接口是否存在，以及请求参数是否正确
     * @param path
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", path).eq("method", method);

        InterfaceInfo one = interfaceInfoService.getOne(queryWrapper);

        return one;
    }

    public static void main(String[] args) {

    }
}
