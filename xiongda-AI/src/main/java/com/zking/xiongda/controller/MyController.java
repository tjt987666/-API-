package com.zking.xiongda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器类，用于处理与AI聊天相关的HTTP请求。
 */
@RestController
@Slf4j
class MyController {

    /**
     * AI聊天客户端，用于与AI模型进行交互。
     */
    private final ChatClient chatClient;

    /**
     * 构造函数，通过依赖注入初始化ChatClient。
     *
     * @param chatClientBuilder ChatClient的构建器
     */
    @Autowired
    public MyController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * 处理GET请求，根据用户输入生成AI响应。
     *
     * @param userInput 用户输入的文本
     * @return AI生成的响应内容
     */
    @GetMapping("/ai")
    String generation(@RequestParam("userInput") String userInput) {
        // 调用AI聊天客户端生成响应内容
        String content = this.chatClient.prompt()
                .user(userInput) // 设置用户输入
                .call() // 调用AI模型
                .content(); // 获取AI生成的内容



        return content;
    }
}
