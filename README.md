# 一、项目概述

智汇API接口平台是基于React+Spring Boot+Dubbo+GateWay的API接口调用平台，管理员可以接入并发布接口，可视化接口调用情况；用户可以开通接口调用权限，浏览接口以及在线调试，并通过客户端的SDK轻松调用接口。做这个项目是为了主要是为了学技术，因为在之前我没有做过微服务相关的内容，也是对自己的一个阶段性的提升。

## 项目架构

### 基础API架构
![image-20241213111033978](https://github.com/user-attachments/assets/3394b913-eea9-4b22-b6e1-e261631fb34f)



在基础版的API架构中我们可以看到，如果我们自己研发产品上线的话，要依赖别人的产品维持，那么这是不长久的，如果自己开发，在成本上又太高了，有没有什么办法，可以解决这种项目不是简单，易维护、成本降低、用户体更好的方法，当然有！！，他就是AI，或许到这里小伙伴们可能会有疑问，我这个用AI干嘛，AI不是帮我看报错这些其他的问题吗，nonono，你局限了，Spring官方早就推出了Spring AI alibab，可以很好的在项目中接入AI程序，那么现在回到我们的问题，我开发者开发一个获取天气的接口，我个人要调用别人的接口或者自己爬天气网的信息，那么我们换个思位想，我能不能让AI来做这些事情呢？并且AI还可以做的更好，AI的原理其实根据你的信息通过他们的逻辑去爬取对应的数据，然后在用他们的算法整理出来，是不是就简单多了，当然，这是个思想，大家也不要去抗拒这个AI，我觉得我们程序员就是应该不断的提升自己接受新的技术，这样才不会被淘汰，并且AI的到来在未来的开发中说不定会接入我们的传统行业中，我们不一定要特别特别精，我们要了解，有这个思想。

### AI版API架构

![image-20241213111127983](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20241213111127983.png)

#### AI效果图

如果图片异常显示，请到我的个人语雀查看：

https://www.yuque.com/tanjiantao/bn92e3/yzz1ch33b1407cbi?singleDoc# 《智汇API接口平台》

![image.png](https://cdn.nlark.com/yuque/0/2024/png/40869722/1734010326542-c4a6734d-c92e-4fe3-a13e-d168bc342724.png?x-oss-process=image%2Fformat%2Cwebp%2Fresize%2Cw_937%2Climit_0)

#### 模块介绍

![image.png](https://cdn.nlark.com/yuque/0/2024/png/40869722/1733990610628-b8726439-7976-41b9-aa47-77d21043dff6.png?x-oss-process=image%2Fformat%2Cwebp)

6个模块：
1. **xiongda-backend（后台管理系统）**：核心业务模块后端，负责用户管理和接口管理，系统安全等核心业务功能。
2. **xiongda-client-SDK（第三方调用SDK）**: 客户端的SDK，封装了对各种API接口调用的方法，降低开发者使用的成本。
3. **xiongda-common（公共模块）**: 公共模块，储放一些其他模块中需要复用的方法、工具类、实体类等。
4. **xiongda-AI（AI大模型系统）**：提供AI服务的模块，用于接入系统，处理用户请求的信息响应结果
5. **xiongda-gateway（API网关系统）**: API网关，负责集中的路由转发、统一鉴权、统一业务处理、访问控制等。
6. **xiongda-interface（接口服务系统）**: 真正提供API服务的接口

**注意！！！**
因为这个AI模块我也是在后面才想到的，但是我的项目是JDK1.8+SpringBoot2开发的，AI最低是JDK17+SpringBoot3运行，要单独打开并且配置17的JDK运行（IDEA设置直接改就好），所以我还在改造项目，哈哈没办法时间太紧张了，还要准备到时候面试。

### 项目展示

- 用户登录模块

  ![用户登录模块](E:\智汇API接口平台\用户登录模块.png)

- 接口主页展示模块

  ![接口主页展示模块](E:\智汇API接口平台\接口主页展示模块.png)

- 接口调用详情模块

  ![接口调用详情模块](E:\智汇API接口平台\接口调用详情模块.png)

- 接口管理模块

  ![接口管理模块](E:\智汇API接口平台\接口管理模块.png)

- 接口调用情况分析模块

  ![接口调用情况分析模块](E:\智汇API接口平台\接口调用情况分析模块.png)

## 技术选形

### 前端
1. React 18
2. Ant Design Pro 5.x 脚手架
3. Ant Design & Procomponents 组件库
4. Umi 4 前端框架
5. OpenAPI 前端代码生成

### 后端
1. Java Spring Boot
2. MySQL 数据库
3. MyBatis-Plus 及 MyBatis X 自动生成
4. API 签名认证（Http 调用）
5. Spring Boot Starter（SDK 开发）
6. Dubbo 分布式（RPC、Nacos）
7. Swagger + Knife4j 接口文档生成
8. Spring Cloud Gateway 微服务网关
9. Hutool、Apache Common Utils、Gson 等工具库
10. Spring AI alibaba AI大模型

## 项目阶段实现

### 第一阶段：项目诞生
1. 项目的背景分析和计划
2. 对项目中需求的分析
3. 对项目中的业务流程分析
4. 设计最基本的架构图和系统
5. 项目中的技术选型
6. 对数据库表设计
7. 项目初始化 | 前端 Ant Design Pro 框架
8. 项目初始化 | 后端 Spring Boot
9. 代码自动生成 | 后端 Swagger 文档
10. 代码自动生成 | 前端 Open API（强推，大幅提高效率）

### 第二阶段：核心业务开发
1. 用户登录页面开发
2. 接口管理功能开发（Ant Design 高级组件）
3. 模拟接口项目 | 示例接口开发
4. 模拟接口项目 | HTTP 客户端调用
5. API 签名认证详解及实战
6. 客户端 SDK 开发（Spring Boot Starter）
7. 接口管理功能 | 发布 / 下线接口开发
8. 接口管理功能 | 前端页面开发
9. 接口列表页面开发
10. 在线调试接口功能 | 后端接口开发
11. 在线调试接口功能 | 前端页面开发

### 第三阶段：开发及优化
1. 接口调用统计功能 | 后端开发
2. 接口调用统计功能 | 优化方案分析及对比
3. API 网关 | Spring Cloud Gateway 实现
4. 接口调用统计功能 | 统一业务处理（鉴权 + 统计）
5. 分布式改造 | 公共模块抽象
6. 分布式改造 | Dubbo 远程调用
7. 管理员统计分析功能 | 前端可视化
8. 管理员统计分析功能 | 后端聚合查询接口开发

### 第四阶段：扩展与部署
1. 接入Spring AI alibaba
2. 项目改造为JDK17+Spring Boot3
3. 使用Docker部署上线
4. 后期维护
