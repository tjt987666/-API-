package com.xiongda.xiongdainterface.controller;

import com.xiongda.xiongclientsdk.model.User;
import com.xiongda.xiongclientsdk.utils.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/name")
public class NameController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;




    @GetMapping("/get")
    public String getName(String name){
        return "you  name is :"+name;
    }


    @PostMapping("/post")
    public String postName(@RequestParam String name){

        return "you post name is"+name;
    }

    @PostMapping("/user")
    public String getUserNmae(@RequestBody User user, HttpServletRequest request){

//        String accessKey = request.getHeader("accessKey"); // 实际情况数据库获取这里模拟获取到
//        String nonce = request.getHeader("nonce"); // redis储存，在一次请求在redis中读取并且对比有效期
//        String timestamp = request.getHeader("timestamp");
//        String body = request.getHeader("body");
//        String sign = request.getHeader("sign");


        return "you name is :"+user.getUsername();

    }



}



