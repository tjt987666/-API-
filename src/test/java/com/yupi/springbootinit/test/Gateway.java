package com.yupi.springbootinit.test;

import java.util.HashMap;
     import java.util.Map;
     import java.util.regex.Matcher;
     import java.util.regex.Pattern;

     public class Gateway {
         private Map<Pattern, String> interfaceMap = new HashMap<>();

         public Gateway() {
             // 初始化接口信息
             interfaceMap.put(Pattern.compile("/api/name/.*"), "http://target-server1.example.com");
             // 其他接口信息
         }

         public String getTargetServer(String path) {
             for (Map.Entry<Pattern, String> entry : interfaceMap.entrySet()) {
                 Matcher matcher = entry.getKey().matcher(path);
                 if (matcher.matches()) {
                     return entry.getValue();
                 }
             }
             return null;
         }

         public void handleRequest(String path) {
             String targetServer = getTargetServer(path);
             if (targetServer == null) {
                 // 处理未找到接口的情况
                 System.out.println("接口不存在");
                 return;
             }
             // 转发请求到目标服务器
             System.out.println("转发请求到: " + targetServer + path);
         }

         public static void main(String[] args) {
             Gateway gateway = new Gateway();
             gateway.handleRequest("/api/name/user");
         }
     }
     