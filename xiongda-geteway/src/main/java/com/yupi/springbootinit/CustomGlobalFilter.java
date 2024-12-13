package com.yupi.springbootinit;

import cn.hutool.core.net.URLEncodeUtil;
import com.alibaba.fastjson.JSON;
import com.xiongda.xiongclientsdk.cilent.XiongdaClient;
import com.xiongda.xiongclientsdk.utils.SignUtils;
import com.yupi.springbootinit.exception.StateException;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.inner.InnerInterfaceInfoService;
import com.yupi.springbootinit.service.inner.InnerUserInterfaceInfoService;
import com.yupi.springbootinit.service.inner.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {


    /**
     * 注入dubbo服务
     */

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;// 统计接口调用次数

    @DubboReference
    private InnerUserService innerUserService; //用户鉴权，查看数据库中是否存在对于的AK用户


    @DubboReference
    private InnerInterfaceInfoService  interfaceInfoService; // 判断数据库是否存在接口




    private static final List<String> IP_HOST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

    private static final String INTERFACE_HOTST = "http://localhost:8123";


    @Autowired
    private StateException stateException;

    /**
     * 过滤器方法，用于处理每个通过网关的请求。
     *
     * @param exchange 服务器Web交换对象，包含请求和响应信息
     * @param chain 过滤器链，用于将请求传递给下一个过滤器或最终的目标服务
     * @return Mono<Void> 表示异步操作完成的信号
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1.请求日志
        ServerHttpRequest request = exchange.getRequest();

        String requestId = request.getId().toString();
        String requestPath = INTERFACE_HOTST+ request.getPath();
        String method = request.getMethodValue();
        MultiValueMap<String, String> queryParams1 = request.getQueryParams();
        String hostString = request.getLocalAddress().getHostString();

        String remoteAddress = request.getRemoteAddress().toString();



        log.info("请求唯一标识: {}", requestId);
        log.info("请求路径: {}", requestPath);
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", queryParams1);
        log.info("请求主机: {}", hostString);
        log.info("远程调用地址: {}", remoteAddress);


        // 获取到响应对象
        ServerHttpResponse response = exchange.getResponse();

        // 2.访问控制,黑白名单
        if (!IP_HOST.contains(hostString)) {
            return stateException.forbidden(response);
        }

        //3.用户鉴权(请求中拿到ak,sk判断是否存在)
        HttpHeaders headers = request.getHeaders();

        String accessKey = headers.getFirst("accessKey");
        // 随机数
        String nonce = headers.getFirst("nonce");
        // 时间戳
        String timestamp = headers.getFirst("timestamp");
//        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");

        User incokeUser = null;
        try {
            // 调用类部服务，根据访问密钥获取用户信息
            incokeUser = innerUserService.getInvokerUser(accessKey);
            System.out.println("用户对象:"+incokeUser);

        }catch (Exception e){
            //补获异常，记录日志
            log.error("用户鉴权日志::" + e.getMessage());
        }

        // 判断用户是否存在
        if (incokeUser == null) {
            log.info("用户不存在"+incokeUser);
            // 返回未授权
            return stateException.forbidden(response);

        }
        // 验证随机数
        if (nonce == null || Long.parseLong(nonce) > 1000000) {
            return stateException.forbidden(response);
        }

       // 验证时间搓
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;

        if (timestamp == null || (currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return stateException.forbidden(response);
        }

//        String body = getRequestBody(exchange);


        // 用户信息中获取到SK
        String secretKey = incokeUser.getSecretKey();

        String body = headers.getFirst("body");

        // 对 body 进行 URL 解码
        try {
            body = URLDecoder.decode(body, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            log.error("URL 解码失败: {}", e.getMessage());
            return stateException.forbidden(response);
        }

//        ServerWebExchange serverWebExchange = setNewHeader(exchange, body);
        System.out.println("Body bytes: " + Arrays.toString(body.getBytes(StandardCharsets.UTF_8)));

        String qwertyuiop = SignUtils.getSign(body, secretKey);




        if (sign == null || !sign.equals(qwertyuiop)) {
//            throw new RuntimeException("签名错误,没有权限");
            log.info("签名错误");
            return stateException.forbidden(response);
        }

        //4.请求接口是否存在
        // todo 从数据库中查看接口是否存在以及请求方式是否匹配,并且是否开启
//         log.info("请求路径: {}", requestPath);
//        log.info("请求方法: {}", method);

        InterfaceInfo interfaceInfo = null;
        try{
            interfaceInfo = interfaceInfoService.getInterfaceInfo(requestPath, method);
        }catch (Exception e){
            // 接口异常记录日志
            log.error("getInterfaceInfo:" + e);
        }
        // 如果接口信息不存在，返回404
        if (interfaceInfo == null) {
            return stateException.apiNotFound(response,"接口不存在");
        }

//        = new StateException();

        //判断用户调用参数是否够用,如果已经没有了，告诉用户调用次数已经用完了
        boolean b = innerUserInterfaceInfoService.checkInterfaceInfo(incokeUser.getId());
        if(!b){
            return stateException.apiNotFound(response, "调用次数已用完");
        }


        // todo 5.请求限制流，控制访问次数 ,如果用户在 5秒内请求超过10次，就返回一个错误提示
        // todo 可能还要涉及到用户记费的功能，在数据库中查询到他的请求次数已经不够了，就拦截这个请求告诉他该充钱了
        //常见的限流算法包括计数器限流算法、滑动窗口限流算法、漏桶限流算法和令牌桶限流算法。


        //  5.请求转发，调用模拟接口
        // todo 异步问题,应该是在调用完接口之后才返回，但是这里直接返回了，没有在调用完接口之后才返回，所以这里需要异步处理
        // todo 因为chain.filter(exchange);是一个异步处理，我们可以用response装饰者,把响应对象包装起来，在调用完接口之后才返
        // 调用增强类
        // 5.请求转发，调用模拟接口
        return handleResponse(exchange, chain,incokeUser.getId(),interfaceInfo.getId());
    }


    /**
     * 装饰者模式，对这个进行增强
     *
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long userId, long interfaceInfoId) {
        try {
            System.out.println("请求头信息");
            log.info("响应头信息" + exchange);
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if (statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                log.info(data);

                                // 6.响应日志

                                // todo 7.调用成功接口次数加1,调用其他模块的方法记费，减少在网关从复写CURD
                                log.info("ok,数据库次数+1");

                                try {
                                    innerUserInterfaceInfoService.InvokeCount(interfaceInfoId, userId);
                                }catch (Exception e){
                                    // 异常处理记录,报警系统，出现问题触发报警机制
                                    log.error("InvokeCount error:" + e);

                                }
                                // 获取到接口ID信息,对数据库中这个接口的调用次数加一
                                // 获取到用户ID信息，对用户的调用总次数加一，剩余次数减一

                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 调用请求失败返回错误信息
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        } catch (Exception e) {
            // 处理异常情况，记录错误日志
            log.error("网关处理响应异常:" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
