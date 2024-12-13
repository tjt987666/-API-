package com.yupi.springbootinit;

import com.yupi.springbootinit.provider.DemoService;
import com.yupi.springbootinit.provider.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * 主应用程序类，用于启动 Spring Boot 应用。
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class, // 排除数据源自动配置
        DataSourceTransactionManagerAutoConfiguration.class, // 排除数据源事务管理器自动配置
        HibernateJpaAutoConfiguration.class}) // 排除 JPA 自动配置
@EnableDubbo // 启用 Dubbo 支持
@Service // 将当前类注册为一个 Spring Bean
public class XiongdaGetewayApplication {

    @DubboReference // 注入 Dubbo 提供的服务
    private DemoService demoService;

    // @DubboReference // 注入另一个 Dubbo 提供的服务
    // private UserService userService;

    /**
     * 主方法，用于启动应用程序。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(XiongdaGetewayApplication.class, args); // 启动 Spring Boot 应用

        XiongdaGetewayApplication application = context.getBean(XiongdaGetewayApplication.class); // 获取当前应用实例
        String result = application.doSayHello("world"); // 调用 sayHello 方法
        String result2 = application.doSayHello2("world"); // 调用 sayHello2 方法
        System.out.println("result: " + result); // 打印结果
        System.out.println("result: " + result2); // 打印结果

        // String result3 = application.logins("你好"); // 调用 login 方法
        // System.out.println("result: " + result3); // 打印结果
    }

    // /**
    //  * 调用 UserService 的 login 方法。
    //  *
    //  * @param name 用户名
    //  * @return 登录结果
    //  */
    // public String logins(String name) {
    //     return userService.login(name);
    // }

    /**
     * 调用 DemoService 的 sayHello 方法。
     *
     * @param name 名称
     * @return 欢迎消息
     */
    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }

    /**
     * 调用 DemoService 的 sayHello2 方法。
     *
     * @param name 名称
     * @return 欢迎消息
     */
    public String doSayHello2(String name) {
        return demoService.sayHello2(name);
    }

    // /**
    //  * 定义自定义路由。
    //  *
    //  * @param builder 路由构建器
    //  * @return 路由定位器
    //  */
    // @Bean
    // public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    //     return builder.routes()
    //             .route("tobaidu", r -> r.path("/baidu") // 定义到百度的路由
    //                     .uri("https://www.baidu.com"))
    //             .route("toyupiicu", r -> r.path("/yupiicu") // 定义到 yupi.icu 的路由
    //                     .uri("http://yupi.icu"))
    //             .build();
    // }
}
