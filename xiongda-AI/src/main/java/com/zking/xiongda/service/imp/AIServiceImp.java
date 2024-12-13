package com.zking.xiongda.service.imp;

import com.zking.xiongda.service.AIService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 声明为Dubbo服务注册要nacos注册中心，用于其他系统调用
 *
 */
//@DubboService
@Service
public class AIServiceImp implements AIService {

    /**
     * AI聊天客户端，用于与AI模型进行交互。
     */
    private ChatClient chatClient;
    @Autowired
    public void MyController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String getAIData(String input) {

        if (input.equals("")) {
            throw new IllegalArgumentException("参数为空");
        }

        String content = this.chatClient.prompt()
                .user(input) // 设置用户输入
                .call() // 调用AI模型
                .content(); // 获取AI生成的内容

        System.out.println("调用AI大模型");



        return content;
    }
}
