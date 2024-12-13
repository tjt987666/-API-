package com.yupi.springbootinit.controller;

import com.google.gson.Gson;
import com.xiongda.xiongclientsdk.cilent.XiongdaClient;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.UserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/apiInfo")
public class ApiController {

    @Autowired
    private UserService userService;

    @RequestMapping("/weather")
    public BaseResponse<Object> weather(@RequestParam("weather") String weather, HttpServletRequest request) {
        System.out.println(weather);

        // 获取到登录用户
        User loginUser = userService.getLoginUser(request);

        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        String userStatus = loginUser.getUserStatus();

        if (accessKey == null || secretKey == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户未登录");
        }

        if (userStatus.equals("1")){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户被拉黑");
        }
        if (userStatus.equals("2")){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户被封号");
        }

        // 将用户的信息传入到客户端
        XiongdaClient client = new XiongdaClient(accessKey,secretKey);
        Gson gson = new Gson();

        // 获取到请求参数信息
        // 调用客户端
        try {
            String weatherName = client.getWeather(weather);

            return ResultUtils.success(weatherName);
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"调用客户端失败");
        }

    }


}
