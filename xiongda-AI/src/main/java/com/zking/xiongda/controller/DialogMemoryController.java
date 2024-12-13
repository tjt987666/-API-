package com.zking.xiongda.controller;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对话记忆控制器
 */
@RestController
public class DialogMemoryController {

    private final Map<String, List<Message>> conversationMemory = new ConcurrentHashMap<>();
    private final DashScopeApi dashScopeApi = new DashScopeApi("sk-151f3412366c43e9919d4e5d98e8db43");
    private final DashScopeChatModel chatModel = new DashScopeChatModel(dashScopeApi);

    /**
     * # 初始化对话
     * http://localhost:8480/init?sessionId=123
     *
     * # 第一轮对话
     * http://localhost:8480/chat?sessionId=123&userInput=我想去新疆
     *
     * # 第二轮对话
     * http://localhost:8480/chat?sessionId=123&userInput=能帮我推荐一些旅游景点吗?
     *
     * # 第三轮对话
     * http://localhost:8480/chat?sessionId=123&userInput=那里这两天的天气如何?
     */
    @RequestMapping("/chat")
    public String chat(@RequestParam String sessionId, @RequestParam String userInput) {
        // 获取或初始化对话历史
        List<Message> messages = conversationMemory.computeIfAbsent(sessionId, k -> new ArrayList<>());

        // 添加用户消息
        messages.add(new UserMessage(userInput));

        // 调用聊天模型
        ChatResponse response = chatModel.call(new Prompt(messages));
        String content = response.getResult().getOutput().getContent();


        // 添加助手消息
        messages.add(new AssistantMessage(content));

        // 返回助手的消息内容
        return content;
    }

    @RequestMapping("/init")
    public String initConversation(@RequestParam String sessionId) {
        // 初始化对话历史，添加系统消息
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("你是一个旅游规划师"));
        conversationMemory.put(sessionId, messages);
        return "Conversation initialized.";
    }
}
